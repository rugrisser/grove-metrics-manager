package org.grove.metricsmanager.schedule.model.mapping

import org.grove.metricsmanager.schedule.exception.MappingException
import org.grove.metricsmanager.schedule.model.TaskResult
import org.grove.protobuf.taskmanager.MetricTaskResult
import org.grove.protobuf.taskmanager.MetricTaskResultStatus
import java.util.*

val MetricTaskResult.internalModel get() =
    TaskResult(
        metricId = UUID.fromString(metricId),
        status = status.internalModel
    )

val MetricTaskResultStatus.internalModel get() = when (this) {
    MetricTaskResultStatus.SUCCESSFUL -> TaskResult.Status.SUCCESSFUL
    MetricTaskResultStatus.FAILED -> TaskResult.Status.FAILED
    else -> throw MappingException("Unable to map Protobuf enum type MetricTaskResultStatus")
}
