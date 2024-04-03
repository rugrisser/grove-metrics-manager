package org.grove.metricsmanager.scheduler

import jakarta.annotation.PostConstruct
import org.grove.metricsmanager.common.service.MetricsService
import org.grove.metricsmanager.scheduler.dao.ScheduleDao
import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
@EnableScheduling
class TaskSchedulerService(
    private val scheduleDao: ScheduleDao,
    private val metricsService: MetricsService
) {

    private var mappedSchedule = mapOf<UUID, ScheduleItem>()

    @PostConstruct
    fun init() {
        mappedSchedule = scheduleDao
            .loadSchedule()
            .associateBy { it.metric.id }

        logger.info("Restored ${mappedSchedule.size} schedule items")
    }

    @Scheduled(
        fixedRate = 30,
        initialDelay = 30,
        timeUnit = TimeUnit.SECONDS
    )
    fun renewSchedule() {
        logger.info("Renewing schedule...")
        mappedSchedule += metricsService
            .getMetricsWithoutScheduledTasks(mappedSchedule.keys)
            .map { ScheduleItem(metric = it) }
            .apply { scheduleDao.updateSchedule(this) }
            .associateBy { it.metric.id }

        logger.info("Schedule renewed")
    }

    @Scheduled(
        fixedRate = 15,
        initialDelay = 20,
        timeUnit = TimeUnit.SECONDS
    )
    fun batchRunTasks() {
        logger.info("Sending tasks batch")



        logger.info("Batch sent successfully")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(TaskSchedulerService::class.java)
    }
}
