package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Source
import java.util.UUID

data class UpdateSourceRequestDto(
    val id: UUID,
    val name: String,
    val link: String
): RequestModel<Source> {

    override fun toModel(): Source =
        Source(id, name, link)
}