package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.dto.response.ErrorResponseDto
import org.grove.metricsmanager.api.dto.response.fromException
import org.grove.metricsmanager.api.exception.ControllerException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ControllerException::class)
    fun handleControllerException(exception: ControllerException) =
        ResponseEntity(ErrorResponseDto.fromException(exception), exception.httpStatus)
}
