package org.grove.metricsmanager.api.exception

import org.springframework.http.HttpStatus

open class ControllerException(
    val httpStatus: HttpStatus,
    override val message: String,
    cause: Throwable?
) : RuntimeException(message, cause)
