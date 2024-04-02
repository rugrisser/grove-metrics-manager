package org.grove.metricsmanager.common.service

import org.grove.metricsmanager.api.dto.request.CreateMetricRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateMetricRequestDto
import org.grove.metricsmanager.common.dao.MetricsDao
import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.Metric
import org.grove.metricsmanager.common.entity.Source
import org.grove.metricsmanager.common.exception.MetricNotFoundException
import org.grove.metricsmanager.common.util.processDatabaseException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MetricsService(
    private val metricsDao: MetricsDao,
    private val sourcesService: SourcesService,
    private val consumersService: ConsumersService
) {

    fun getAllMetrics(): List<Metric> {
        return runCatching {
            metricsDao.getAllMetrics()
        }.getOrElse(::processDatabaseException)
    }

    fun getMetricsBySourceId(sourceId: UUID): List<Metric> {
        return runCatching {
            metricsDao.getMetricsBySourceId(sourceId)
        }.getOrElse(::processDatabaseException)
    }

    fun getMetricById(id: UUID): Metric {
        return runCatching {
            metricsDao.getMetricById(id)
        }.getOrElse(::processDatabaseException) ?: throw MetricNotFoundException()
    }

    fun getMetricsWithoutScheduledTasks(ignore: Collection<UUID>): List<Metric> {
        return kotlin.runCatching {
            metricsDao.getMetricsExceptGivenList(ignore)
        }.getOrElse(::processDatabaseException)
    }

    fun createMetric(dto: CreateMetricRequestDto): UUID {
        val pair = findSourceAndConsumer(dto.sourceId, dto.consumerId)
        val source = pair.first
        val consumer = pair.second

        val metric = dto.toModel(source, consumer)

        return runCatching {
            metricsDao.createOrUpdateMetric(metric)
            metric.id
        }.getOrElse(::processDatabaseException)
    }

    fun updateMetric(dto: UpdateMetricRequestDto) {
        if (metricsDao.getMetricById(dto.id) == null) {
            throw MetricNotFoundException()
        }

        val pair = findSourceAndConsumer(dto.sourceId, dto.consumerId)
        val source = pair.first
        val consumer = pair.second

        val metric = dto.toModel(source, consumer)

        runCatching {
            metricsDao.createOrUpdateMetric(metric)
        }.onFailure(::processDatabaseException)
    }

    fun deleteMetricById(id: UUID) {
        runCatching {
            metricsDao.deleteMetric(id)
        }.onFailure(::processDatabaseException)
    }

    private fun findSourceAndConsumer(
        sourceId: UUID,
        consumerId: UUID
    ): Pair<Source, Consumer> {
        return sourcesService.getSourceById(sourceId) to consumersService.getConsumerById(consumerId)
    }
}
