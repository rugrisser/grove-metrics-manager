package org.grove.metricsmanager.scheduler.broker.output.impl

import org.grove.metricsmanager.scheduler.broker.output.SenderService
import org.grove.metricsmanager.scheduler.configuration.properties.KafkaProperties
import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import org.grove.metricsmanager.scheduler.model.protobuf.protobufModel
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaSenderService(
    private val kafkaProperties: KafkaProperties,
    private val kafkaTemplate: KafkaTemplate<UUID, ByteArray>
): SenderService {

    override fun sendBatch(messagesBatch: Map<UUID, ScheduleItem>) {
        val outputBatch = mapToExternalModel(messagesBatch)
        val topicName = kafkaProperties.producer.name

        outputBatch.forEach {
            kafkaTemplate.send(topicName, it.key, it.value.toByteArray())
        }
    }

    private fun mapToExternalModel(messagesBatch: Map<UUID, ScheduleItem>) =
        messagesBatch
            .mapValues { it.value.protobufModel }
}
