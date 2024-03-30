package org.grove.metricsmanager.common.dao

import org.grove.metricsmanager.common.exception.ConsumerNotFoundException
import org.grove.metricsmanager.common.entity.Consumer
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ConsumersDao(
    private val sessionFactory: SessionFactory,
) {

    fun getAllConsumers(): List<Consumer> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(Consumer::class.java)
            val root = query.from(Consumer::class.java)
            val allSources = query.select(root)

            it.createQuery(allSources).resultList
        }
    }

    fun getConsumerById(consumerId: UUID): Consumer? {
        return sessionFactory.fromSession {
            it.get(Consumer::class.java, consumerId)
        }
    }

    fun createCustomer(consumer: Consumer) {
        sessionFactory.inTransaction {
            it.merge(consumer)
        }
    }

    fun updateCustomer(consumer: Consumer) {
        sessionFactory.inTransaction {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createCriteriaDelete(Consumer.Property::class.java)
            val root = query.from(Consumer.Property::class.java)
            val deleteProperties = query.where(
                criteriaBuilder.equal(
                    root.get<UUID>("consumerId"),
                    consumer.id
                )
            )

            it.createMutationQuery(deleteProperties).executeUpdate()
            it.merge(consumer)
        }
    }

    fun deleteConsumer(consumerId: UUID) {
        val consumer = getConsumerById(consumerId) ?: throw ConsumerNotFoundException()

        sessionFactory.inTransaction {
            it.remove(consumer)
        }
    }
}
