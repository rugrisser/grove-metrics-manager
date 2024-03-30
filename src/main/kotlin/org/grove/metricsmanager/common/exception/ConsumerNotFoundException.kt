package org.grove.metricsmanager.common.exception

import org.springframework.http.HttpStatus

class ConsumerNotFoundException(
    cause: Throwable? = null
): ServiceException(
    HttpStatus.NOT_FOUND,
    "Consumer not found",
    cause
)
