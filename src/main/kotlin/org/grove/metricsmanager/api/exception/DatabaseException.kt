package org.grove.metricsmanager.api.exception

import org.springframework.http.HttpStatus

class DatabaseException(
    message: String,
    cause: Throwable?
) : ControllerException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    message,
    cause
)
