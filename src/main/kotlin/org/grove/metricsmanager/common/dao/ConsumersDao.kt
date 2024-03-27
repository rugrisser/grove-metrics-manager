package org.grove.metricsmanager.common.dao

import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class ConsumersDao(
    private val sessionFactory: SessionFactory,
) {
}
