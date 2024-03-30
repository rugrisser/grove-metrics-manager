package org.grove.metricsmanager.common.service

import org.grove.metricsmanager.api.dto.request.CreateConsumerRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateConsumerRequestDto
import org.grove.metricsmanager.common.exception.ConsumerNotFoundException
import org.grove.metricsmanager.common.dao.ConsumersDao
import org.grove.metricsmanager.common.entity.Consumer
import org.grove.metricsmanager.common.util.processDatabaseException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ConsumersService(
    private val consumersDao: ConsumersDao
) {

    fun getAllConsumers(): List<Consumer> {
        return runCatching {
            consumersDao.getAllConsumers()
        }.getOrElse(::processDatabaseException)
    }

    fun getConsumerById(id: UUID): Consumer {
        return runCatching {
            consumersDao.getConsumerById(id)
        }.getOrElse(::processDatabaseException) ?: throw ConsumerNotFoundException()
    }

    fun createConsumer(requestDto: CreateConsumerRequestDto): UUID {
        val consumer = requestDto.toModel()

        return runCatching {
            consumersDao.createCustomer(consumer)
            consumer.id
        }.getOrElse(::processDatabaseException)
    }

    fun updateConsumer(requestDto: UpdateConsumerRequestDto) {
        val consumer = requestDto.toModel()
        if (consumersDao.getConsumerById(consumer.id) == null) {
            throw ConsumerNotFoundException()
        }

        runCatching {
            consumersDao.updateCustomer(consumer)
        }.onFailure(::processDatabaseException)
    }

    fun deleteConsumer(id: UUID) {
        runCatching {
            consumersDao.deleteConsumer(id)
        }.onFailure(::processDatabaseException)
    }
}
