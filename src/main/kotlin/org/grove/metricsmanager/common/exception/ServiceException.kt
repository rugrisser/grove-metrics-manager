package org.grove.metricsmanager.common.exception

import org.springframework.http.HttpStatus

open class ServiceException(
    val httpStatus: HttpStatus,
    override val message: String,
    cause: Throwable?
) : RuntimeException(message, cause)
