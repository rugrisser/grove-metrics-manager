package org.grove.metricsmanager.api.dto.response

import org.grove.metricsmanager.common.exception.ServiceException

data class ErrorResponseDto(
    val message: String
) {
    companion object
}

fun ErrorResponseDto.Companion.fromException(exception: ServiceException) =
    ErrorResponseDto(exception.message)
