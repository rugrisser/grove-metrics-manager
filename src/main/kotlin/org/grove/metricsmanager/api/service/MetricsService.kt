package org.grove.metricsmanager.api.service

import org.grove.metricsmanager.common.dao.MetricsDao
import org.grove.metricsmanager.common.entity.Metric
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MetricsService(
    private val metricsDao: MetricsDao
) {

//    fun getAllMetrics(): List<Metric> {
//
//    }

    fun getMetricsBySourceId(sourceId: UUID) {

    }

//    fun getMetricById(metricId: UUID): Metric {
//
//    }

    fun deleteMetricById(metricId: UUID) {

    }
}
