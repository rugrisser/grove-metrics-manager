package org.grove.metricsmanager.common.dao

import org.grove.metricsmanager.common.exception.SourceNotFoundException
import org.grove.metricsmanager.common.entity.Source
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class SourcesDao(
    private val sessionFactory: SessionFactory
) {

    fun getAllSources(): List<Source> {
        return sessionFactory.fromSession {
            val criteriaBuilder = it.criteriaBuilder
            val query = criteriaBuilder.createQuery(Source::class.java)
            val root = query.from(Source::class.java)
            val allSources = query.select(root)

            it.createQuery(allSources).resultList
        }
    }

    fun getSourceById(sourceId: UUID): Source? {
        return sessionFactory.fromSession {
            it.get(Source::class.java, sourceId)
        }
    }

    fun createOrUpdateSource(source: Source) {
        sessionFactory.inTransaction {
            it.merge(source)
        }
    }

    fun deleteSource(sourceId: UUID) {
        val source = getSourceById(sourceId) ?: throw SourceNotFoundException()

        sessionFactory.inTransaction {
            it.remove(source)
        }
    }
}