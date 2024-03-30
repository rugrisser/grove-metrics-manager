package org.grove.metricsmanager.common.exception

import org.springframework.http.HttpStatus

class DatabaseException(
    message: String,
    cause: Throwable?
) : ServiceException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    message,
    cause
)
