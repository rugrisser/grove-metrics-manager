package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.ConsumerType
import java.util.UUID

data class UpdateConsumerRequestDto(
    val id: UUID,
    val name: String,
    val type: ConsumerType,
    val properties: Set<ConsumerPropertyDto>
): RequestModel<Consumer> {

    override fun toModel(): Consumer =
        Consumer(
            id = id,
            name = name,
            type = type
        ).also { model ->
            model.properties = this.properties
                .map { it.toModel(this.id) }
                .toMutableSet()
        }
}
