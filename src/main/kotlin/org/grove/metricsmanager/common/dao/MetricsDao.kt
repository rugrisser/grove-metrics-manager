package org.grove.metricsmanager.common.dao

import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class MetricsDao(
    private val sessionFactory: SessionFactory
) {

}
