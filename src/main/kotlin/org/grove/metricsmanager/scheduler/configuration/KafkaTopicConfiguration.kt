package org.grove.metricsmanager.scheduler.configuration

import org.apache.kafka.clients.admin.NewTopic
import org.grove.metricsmanager.scheduler.configuration.properties.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaTopicConfiguration {

    @Bean
    fun mainProducerTopic(properties: KafkaProperties): NewTopic {
        val producerProperties = properties.producer
        return NewTopic(
            producerProperties.name,
            producerProperties.partitionsCount,
            producerProperties.replicationFactor
        )
    }
}
