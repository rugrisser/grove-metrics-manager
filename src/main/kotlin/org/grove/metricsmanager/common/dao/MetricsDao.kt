package org.grove.metricsmanager.common.dao

import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.Metric
import org.grove.metricsmanager.common.entity.Source
import org.grove.metricsmanager.common.exception.MetricNotFoundException
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MetricsDao(
    private val sessionFactory: SessionFactory
) {

    fun getAllMetrics(): List<Metric> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(Metric::class.java)
            val root = query.from(Metric::class.java)
            val allMetrics = query.select(root)

            it.createQuery(allMetrics).resultList
        }
    }

    fun getMetricsBySourceId(sourceId: UUID): List<Metric> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(Metric::class.java)
            val root = query.from(Metric::class.java)
            val metricsBySource = query
                .select(root)
                .where(
                    criteriaBuilder.equal(
                        root
                            .get<Source>("source")
                            .get<UUID>("id"),
                        sourceId
                    )
                )

            it.createQuery(metricsBySource).resultList
        }
    }

    fun getMetricById(metricId: UUID): Metric? {
        return sessionFactory.fromSession {
            it.get(Metric::class.java, metricId)
        }
    }

    fun getMetricsExceptGivenList(ignore: Collection<UUID>): List<Metric> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(Metric::class.java)
            val root = query.from(Metric::class.java)
            val metrics = query
                .select(root)
                .where(
                    criteriaBuilder.and(
                        criteriaBuilder.not(
                            root
                                .get<UUID>("id")
                                .`in`(ignore)
                        ),
                        criteriaBuilder.isNotNull(root.get<Source?>("source")),
                        criteriaBuilder.isNotNull(root.get<Consumer?>("consumer"))
                    )
                )

            it.createQuery(metrics).resultList
        }
    }

    fun createOrUpdateMetric(metric: Metric) {
        sessionFactory.inTransaction {
            it.merge(metric)
        }
    }

    fun deleteMetric(metricId: UUID) {
        val metric = getMetricById(metricId) ?: throw MetricNotFoundException()

        sessionFactory.inTransaction {
            it.remove(metric)
        }
    }
}
