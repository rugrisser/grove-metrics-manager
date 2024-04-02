package org.grove.metricsmanager.scheduler.dao

import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class ScheduleDao(
    private val sessionFactory: SessionFactory
) {

    fun loadSchedule(): List<ScheduleItem> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(ScheduleItem::class.java)
            val root = query.from(ScheduleItem::class.java)
            val allSources = query.select(root)

            it.createQuery(allSources).resultList
        }
    }

    fun updateSchedule(newItems: List<ScheduleItem>) {
        sessionFactory.inTransaction {
            newItems.forEach(it::merge)
        }
    }
}
