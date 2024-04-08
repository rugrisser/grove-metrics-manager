package org.grove.metricsmanager.scheduler.model.protobuf

import com.google.protobuf.timestamp
import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.entity.ConsumerType
import org.grove.metricsmanager.scheduler.entity.ScheduleItem
import org.grove.metricsmanager.scheduler.exception.MappingException
import org.grove.protobuf.metricsmanager.*
import org.grove.protobuf.metricsmanager.ConsumerType as ConsumerTypeProtobuf
import java.time.ZoneOffset

val ScheduleItem.protobufModel: MetricTask get() {
    val scheduleItem = this

    return metricTask {
        metricId = scheduleItem.metric.id.toString()
        metricKey = scheduleItem.metric.key
        processTo = timestamp {
            seconds = scheduleItem.plannedAt.toEpochSecond(ZoneOffset.UTC)
        }
        source = source {
            link = scheduleItem.metric.source?.link ?: throw MappingException(
                "Protobuf message MetricTask requires non-null source"
            )
        }
        consumer = consumer {
            val consumer = scheduleItem.metric.consumer ?: throw MappingException(
                "Protobuf message MetricTask requires non-null consumer"
            )

            id = consumer.id.toString()
            type = consumer.type.protobufType
            properties.addAll(consumer.properties.map(Consumer.Property::protobufModel))
        }
    }
}

internal val ConsumerType.protobufType: ConsumerTypeProtobuf get() {
    return when(this) {
        ConsumerType.PROMETHEUS -> ConsumerTypeProtobuf.PROMETHEUS
        ConsumerType.CLICKHOUSE -> ConsumerTypeProtobuf.CLICKHOUSE
        ConsumerType.PROMETHEUS_PUSHGATEWAY -> ConsumerTypeProtobuf.PROMETHEUS_PUSHGATEWAY
    }
}

internal val Consumer.Property.protobufModel: ConsumerProperty get() {
    val property = this

    return consumerProperty {
        key = property.key
        value = property.value
    }
}
