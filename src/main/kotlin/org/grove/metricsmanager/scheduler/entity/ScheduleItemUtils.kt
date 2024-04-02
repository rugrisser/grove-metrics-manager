package org.grove.metricsmanager.scheduler.entity

import org.grove.metricsmanager.common.entity.Metric
import java.time.LocalDateTime

object ScheduleItemUtils {

    fun planScheduleItem(metric: Metric): LocalDateTime {
        if (metric.ignoreUntil == null) {
            return if (metric.updatedDue != null) {
                metric.updatedDue!! + metric.updateFrequency
            } else {
                LocalDateTime.now()
            }
        }

        if (metric.updatedDue == null) {
            return metric.ignoreUntil!!
        }

        val nextRegularIteration = metric.updatedDue!! + metric.updateFrequency

        return if (nextRegularIteration > metric.ignoreUntil!!) {
            nextRegularIteration
        } else {
            metric.ignoreUntil!!
        }
    }
}
