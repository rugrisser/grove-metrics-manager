package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.dto.response.ErrorResponseDto
import org.grove.metricsmanager.api.dto.response.fromException
import org.grove.metricsmanager.common.exception.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(ServiceException::class)
    fun handleControllerException(exception: ServiceException): ResponseEntity<ErrorResponseDto> {
        when {
            exception.httpStatus.is5xxServerError -> log.error(exception.message, exception)
            else -> log.info(exception.message, exception)
        }

        return ResponseEntity(ErrorResponseDto.fromException(exception), exception.httpStatus)
    }

    private companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
