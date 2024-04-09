package org.grove.metricsmanager.schedule.model

import java.util.UUID

data class TaskResult(
    val metricId: UUID,
    val status: Status
) {

    enum class Status {
        SUCCESSFUL,
        FAILED;
    }
}
