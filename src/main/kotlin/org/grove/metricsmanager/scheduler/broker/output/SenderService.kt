package org.grove.metricsmanager.scheduler.broker.output

import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import java.util.*

interface SenderService {

    fun sendBatch(messagesBatch: Map<UUID, ScheduleItem>)
}
