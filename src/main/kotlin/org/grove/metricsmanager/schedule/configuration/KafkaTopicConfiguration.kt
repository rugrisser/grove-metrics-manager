package org.grove.metricsmanager.schedule.configuration

import org.apache.kafka.clients.admin.NewTopic
import org.grove.metricsmanager.schedule.configuration.properties.KafkaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KafkaProperties::class)
class KafkaTopicConfiguration(
    private val properties: KafkaProperties
) {

    @Bean
    fun mainProducerTopic(): NewTopic {
        val topicConfig = properties.producer
        return NewTopic(
            topicConfig.name,
            topicConfig.partitionsCount,
            topicConfig.replicationFactor
        )
    }

    @Bean
    fun taskManagerConsumerTopic(): NewTopic {
        val topicConfig = properties.taskManagerConsumer.topic
        return NewTopic(
            topicConfig.name,
            topicConfig.partitionsCount,
            topicConfig.replicationFactor
        )
    }
}
