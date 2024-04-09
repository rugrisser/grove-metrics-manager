package org.grove.metricsmanager.scheduler.service

import jakarta.persistence.NoResultException
import org.grove.metricsmanager.common.service.MetricsService
import org.grove.metricsmanager.scheduler.dao.ScheduleDao
import org.grove.metricsmanager.scheduler.exception.ScheduleItemNotFoundException
import org.grove.metricsmanager.scheduler.model.TaskResult
import org.hibernate.NonUniqueResultException
import org.springframework.stereotype.Service

@Service
class TaskResultService(
    private val scheduleDao: ScheduleDao,
    private val metricsService: MetricsService
) {

    fun receiveResult(taskResult: TaskResult) {
        runCatching {
            when (taskResult.status) {
                TaskResult.Status.SUCCESSFUL -> {
                    val scheduleItem = scheduleDao.getScheduleItem(taskResult.metricId)
                    metricsService.actualizeUpdatedDueFieldByMetricId(
                        taskResult.metricId,
                        scheduleItem.plannedAt
                    )
                }
                else -> {}
            }

            scheduleDao.deleteScheduleItem(taskResult.metricId)
        }.onFailure {
            when (it) {
                is NonUniqueResultException -> throw ScheduleItemNotFoundException()
                is NoResultException -> throw ScheduleItemNotFoundException()
                else -> throw it
            }
        }
    }
}
