package org.grove.metricsmanager.common.util

import org.grove.metricsmanager.common.exception.DatabaseException
import org.grove.metricsmanager.common.exception.ServiceException

fun processDatabaseException(throwable: Throwable): Nothing {
    when (throwable) {
        is ServiceException -> throw throwable
        else -> throw DatabaseException("Details not provided", throwable)
    }
}
