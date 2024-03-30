package org.grove.metricsmanager.common.exception

import org.springframework.http.HttpStatus

class SourceNotFoundException(
    cause: Throwable? = null
): ServiceException(
    HttpStatus.NOT_FOUND,
    "Source not found",
    cause
)
