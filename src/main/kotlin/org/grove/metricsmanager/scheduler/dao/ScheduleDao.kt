package org.grove.metricsmanager.scheduler.dao

import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.Metric
import org.grove.metricsmanager.common.entity.Source
import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class ScheduleDao(
    private val sessionFactory: SessionFactory
) {

    fun cleanSchedule() {
        sessionFactory.inTransaction {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createCriteriaDelete(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val hasNull = criteriaBuilder.or(
                criteriaBuilder.isNull(
                    root
                        .get<Metric>("metric")
                        .get<Consumer?>("consumer")
                ),
                criteriaBuilder.isNull(
                    root
                        .get<Metric>("metric")
                        .get<Source?>("source")
                )
            )
            val onlyWithEmptyConsumerOrSource = query.where(
                criteriaBuilder.and(
                    hasNull,
                    criteriaBuilder.equal(
                        root
                            .get<ScheduleItem.Status>("status"),
                        ScheduleItem.Status.CREATED
                    )
                )
            )

            it.createMutationQuery(onlyWithEmptyConsumerOrSource).executeUpdate()
        }
    }

    fun getScheduleItem(id: UUID): ScheduleItem {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val withGivenId = query
                .where(
                    criteriaBuilder.equal(
                        root
                            .get<Metric>("metric")
                            .get<UUID>("id"),
                        id
                    )
                )

            it.createQuery(withGivenId).singleResult
        }
    }

    fun loadSchedule(): List<ScheduleItem> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val allItems = query.select(root)

            it.createQuery(allItems).resultList
        }
    }

    fun loadScheduleItemsToExecute(): List<ScheduleItem> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val join = root.join<ScheduleItem, Metric>("metric")

            val onlyValid = query
                .where(
                    criteriaBuilder.and(
                        criteriaBuilder.isNotNull(join.get<Consumer?>("consumer")),
                        criteriaBuilder.isNotNull(join.get<Source?>("source")),
                        criteriaBuilder.equal(
                            root.get<ScheduleItem.Status>("status"),
                            ScheduleItem.Status.CREATED
                        ),
                        criteriaBuilder.lessThanOrEqualTo(
                            root.get("plannedAt"),
                            LocalDateTime.now()
                        )
                    )
                )

            it.createQuery(onlyValid).resultList
        }
    }

    fun updateSchedule(items: Collection<ScheduleItem>) {
        sessionFactory.inTransaction {
            items.forEach(it::merge)
        }
    }

    fun deleteScheduleItem(id: UUID) {
        sessionFactory.inTransaction {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createCriteriaDelete(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val withGivenId = query
                .where(
                    criteriaBuilder.equal(
                        root
                            .get<Metric>("metric")
                            .get<UUID>("id"),
                        id
                    )
                )

            it.createMutationQuery(withGivenId).executeUpdate()
        }
    }
}
