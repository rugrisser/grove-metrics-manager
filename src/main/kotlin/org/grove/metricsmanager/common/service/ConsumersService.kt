package org.grove.metricsmanager.common.service

import org.grove.metricsmanager.api.dto.request.CreateConsumerRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateConsumerRequestDto
import org.grove.metricsmanager.api.exception.ConsumerNotFoundException
import org.grove.metricsmanager.api.exception.DatabaseException
import org.grove.metricsmanager.common.dao.ConsumersDao
import org.grove.metricsmanager.common.entity.Consumer
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ConsumersService(
    private val consumersDao: ConsumersDao
) {

    fun getAllConsumers(): List<Consumer> {
        return runCatching {
            consumersDao.getAllConsumers()
        }.getOrElse { throw DatabaseException("Details not provided", it) }
    }

    fun getConsumerById(id: UUID): Consumer {
        return runCatching {
            consumersDao.getConsumerById(id)
        }.getOrElse {
            throw DatabaseException("Details not provided", it)
        } ?: throw ConsumerNotFoundException()
    }

    fun createConsumer(requestDto: CreateConsumerRequestDto): UUID {
        val consumer = requestDto.toModel()

        return runCatching {
            consumersDao.createCustomer(consumer)
            consumer.id
        }.getOrElse { throw DatabaseException("Details not provided", it) }
    }

    fun updateConsumer(requestDto: UpdateConsumerRequestDto) {
        val consumer = requestDto.toModel()
        if (consumersDao.getConsumerById(consumer.id) == null) {
            throw ConsumerNotFoundException()
        }

        runCatching {
            consumersDao.updateCustomer(consumer)
        }.onFailure { throw DatabaseException("Details not provided", it) }
    }

    fun deleteConsumer(id: UUID) {
        if (consumersDao.getConsumerById(id) == null) {
            throw ConsumerNotFoundException()
        }

        runCatching {
            consumersDao.deleteConsumer(id)
        }.onFailure { throw DatabaseException("Details not provided", it) }
    }
}
