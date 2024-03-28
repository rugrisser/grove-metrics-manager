package org.grove.metricsmanager.api.exception

import org.springframework.http.HttpStatus

class ConsumerNotFoundException(
    cause: Throwable? = null
): ControllerException(
    HttpStatus.NOT_FOUND,
    "Consumer not found",
    cause
)
