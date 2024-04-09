package org.grove.metricsmanager.schedule.broker.output.impl

import org.grove.metricsmanager.schedule.broker.output.SenderService
import org.grove.metricsmanager.schedule.configuration.properties.KafkaProperties
import org.grove.metricsmanager.schedule.entity.ScheduleItem
import org.grove.metricsmanager.schedule.model.mapping.protobufModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.CompletableFuture

@Service
class KafkaSenderService(
    private val kafkaProperties: KafkaProperties,
    private val kafkaTemplate: KafkaTemplate<UUID, ByteArray>
): SenderService {

    override fun sendBatch(messagesBatch: Map<UUID, ScheduleItem>): Set<UUID> {
        val outputBatch = mapToExternalModel(messagesBatch)
        val topicName = kafkaProperties.producer.name
        val futuresMap = HashMap<UUID, CompletableFuture<SendResult<UUID, ByteArray>>>()
        val failedTasks = HashSet<UUID>()

        outputBatch.forEach {
            kafkaTemplate.send(topicName, it.key, it.value.toByteArray())
                .apply { futuresMap[it.key] = this }
        }

        futuresMap.forEach {
            runCatching {
                it.value.join()
            }.onFailure { e ->
                logger.error("Failed task sending for metric ${it.key}", e)
                failedTasks.add(it.key)
            }
        }

        return failedTasks
    }

    private fun mapToExternalModel(messagesBatch: Map<UUID, ScheduleItem>) =
        messagesBatch
            .mapValues { it.value.protobufModel }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(KafkaSenderService::class.java)
    }
}
