package org.grove.metricsmanager.schedule.broker.output

import org.grove.metricsmanager.schedule.entity.ScheduleItem
import java.util.*

interface SenderService {

    fun sendBatch(messagesBatch: Map<UUID, ScheduleItem>): Set<UUID>
}
