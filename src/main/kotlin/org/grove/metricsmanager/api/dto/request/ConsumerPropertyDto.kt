package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Consumer
import java.util.UUID

data class ConsumerPropertyDto(
    val key: String,
    val value: String
) {

    fun toModel(consumerId: UUID): Consumer.Property =
        Consumer.Property(
            consumerId = consumerId,
            key = key,
            value = value
        )
}