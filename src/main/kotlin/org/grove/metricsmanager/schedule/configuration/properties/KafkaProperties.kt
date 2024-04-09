package org.grove.metricsmanager.schedule.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka")
data class KafkaProperties(
    val producer: Topic,
    val taskManagerConsumer: Consumer
) {

    data class Consumer(
        val topic: Topic,
        val consumerGroupName: String
    )

    data class Topic(
        val name: String,
        val partitionsCount: Int,
        val replicationFactor: Short
    )
}
