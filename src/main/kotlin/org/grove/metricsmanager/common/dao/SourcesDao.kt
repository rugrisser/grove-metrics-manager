package org.grove.metricsmanager.common.dao

import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class SourcesDao(
    private val sessionFactory: SessionFactory
) {

}