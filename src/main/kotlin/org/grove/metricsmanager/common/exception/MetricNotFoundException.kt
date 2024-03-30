package org.grove.metricsmanager.common.exception

import org.springframework.http.HttpStatus

class MetricNotFoundException: ServiceException(
    HttpStatus.NOT_FOUND,
    "Metric not found",
    null
)
