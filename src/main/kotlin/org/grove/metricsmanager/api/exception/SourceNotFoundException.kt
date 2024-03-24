package org.grove.metricsmanager.api.exception

import org.springframework.http.HttpStatus

class SourceNotFoundException(
    cause: Throwable? = null
): ControllerException(
    HttpStatus.NOT_FOUND,
    "Source not found",
    cause
)
