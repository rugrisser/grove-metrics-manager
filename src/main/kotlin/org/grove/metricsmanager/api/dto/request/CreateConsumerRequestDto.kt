package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.ConsumerType

data class CreateConsumerRequestDto(
    val name: String,
    val type: ConsumerType,
    val properties: Set<ConsumerPropertyDto>
): RequestModel<Consumer> {

    override fun toModel(): Consumer =
        Consumer(
            name = name,
            type = type
        ).also { model ->
            model.properties = this.properties
                .map { it.toModel(model.id) }
                .toMutableSet()
        }
}
