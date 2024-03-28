package org.grove.metricsmanager.api.exception

import org.springframework.http.HttpStatus

class ConsumerTypeMismatchingException : ControllerException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    "Unable to restore consumer type",
    null
)