package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.Metric
import org.grove.metricsmanager.common.entity.Source
import java.time.Duration
import java.util.UUID

data class CreateMetricRequestDto(
    val key: String,
    val updateFrequency: Duration,
    val sourceId: UUID,
    val consumerId: UUID
) {

    fun toModel(source: Source, consumer: Consumer) =
        Metric(
            key = key,
            updateFrequency = updateFrequency,
            source = source,
            consumer = consumer
        )
}
