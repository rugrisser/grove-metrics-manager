package org.grove.metricsmanager.api.dto.response

import org.grove.metricsmanager.api.exception.ControllerException

data class ErrorResponseDto(
    val message: String
) {
    companion object
}

fun ErrorResponseDto.Companion.fromException(exception: ControllerException) =
    ErrorResponseDto(exception.message)
