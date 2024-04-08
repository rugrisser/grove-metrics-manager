package org.grove.metricsmanager.scheduler.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka")
data class KafkaProperties(
    val producer: Producer
) {

    data class Producer(
        val name: String,
        val partitionsCount: Int,
        val replicationFactor: Short
    )
}
