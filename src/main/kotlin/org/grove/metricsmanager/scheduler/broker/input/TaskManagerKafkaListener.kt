package org.grove.metricsmanager.scheduler.broker.input

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.grove.metricsmanager.common.exception.MetricNotFoundException
import org.grove.metricsmanager.scheduler.exception.ScheduleItemNotFoundException
import org.grove.metricsmanager.scheduler.model.mapping.internalModel
import org.grove.metricsmanager.scheduler.service.TaskResultService
import org.grove.protobuf.taskmanager.MetricTaskResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.AcknowledgingMessageListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TaskManagerKafkaListener(
    private val taskResultService: TaskResultService
): AcknowledgingMessageListener<UUID, ByteArray> {

    @KafkaListener(
        topics = ["\${kafka.task-manager-consumer.topic.name}"],
        groupId = "\${kafka.task-manager-consumer.consumer-group-name}"
    )
    override fun onMessage(
        data: ConsumerRecord<UUID, ByteArray>,
        acknowledgment: Acknowledgment?
    ) {
        val internalModel = MetricTaskResult
            .parseFrom(data.value())
            .internalModel

        runCatching {
            taskResultService.receiveResult(internalModel)
        }.onFailure {
            when (it) {
                is ScheduleItemNotFoundException -> logger.warn("Schedule item not found")
                is MetricNotFoundException -> logger.warn("Metric not found")
                else -> throw it
            }
        }

        if (acknowledgment == null) {
            logger.warn("Kafka acknowledgement is missing")
        }

        acknowledgment?.acknowledge()
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(TaskManagerKafkaListener::class.java)
    }
}
