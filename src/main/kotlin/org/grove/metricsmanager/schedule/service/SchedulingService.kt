package org.grove.metricsmanager.schedule.service

import org.grove.metricsmanager.common.service.MetricsService
import org.grove.metricsmanager.schedule.broker.output.SenderService
import org.grove.metricsmanager.schedule.dao.ScheduleDao
import org.grove.metricsmanager.schedule.entity.ScheduleItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
@EnableScheduling
class SchedulingService(
    private val scheduleDao: ScheduleDao,
    private val metricsService: MetricsService,
    private val senderService: SenderService
) {

    @Scheduled(
        fixedDelay = 30,
        timeUnit = TimeUnit.SECONDS
    )
    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun renewSchedule() {
        logger.info("Renewing schedule...")

        scheduleDao.cleanSchedule()
        val scheduleOnlyUUID = scheduleDao
            .loadSchedule()
            .map { it.metric.id }

        metricsService
            .getMetricsWithoutScheduledTasks(scheduleOnlyUUID)
            .map { ScheduleItem(metric = it) }
            .apply { scheduleDao.updateSchedule(this) }
            .associateBy { it.metric.id }

        logger.info("Schedule renewed")
    }

    @Scheduled(
        fixedDelay = 15,
        initialDelay = 20,
        timeUnit = TimeUnit.SECONDS
    )
    @Transactional(isolation = Isolation.SERIALIZABLE)
    fun batchRunTasks() {
        logger.info("Sending tasks batch")

        val scheduleToExecute = scheduleDao
            .loadScheduleItemsToExecute()
            .associateBy { it.metric.id }

        val failedIds = senderService.sendBatch(scheduleToExecute)
        scheduleToExecute
            .filter { !failedIds.contains(it.key) }
            .forEach { it.value.status = ScheduleItem.Status.SENT }
        scheduleDao.updateSchedule(scheduleToExecute.values)

        logger.info("Batch sent successfully")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(SchedulingService::class.java)
    }
}
