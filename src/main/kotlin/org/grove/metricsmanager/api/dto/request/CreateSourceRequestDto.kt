package org.grove.metricsmanager.api.dto.request

import org.grove.metricsmanager.common.entity.Source

data class CreateSourceRequestDto(
    val name: String,
    val link: String
): RequestModel<Source> {

    override fun toModel(): Source =
        Source(
            name = name,
            link = link
        )
}
