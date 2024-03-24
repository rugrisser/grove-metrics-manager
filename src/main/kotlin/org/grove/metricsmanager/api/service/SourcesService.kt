package org.grove.metricsmanager.api.service

import org.grove.metricsmanager.api.dto.request.CreateSourceRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateSourceRequestDto
import org.grove.metricsmanager.api.exception.DatabaseException
import org.grove.metricsmanager.api.exception.SourceNotFoundException
import org.grove.metricsmanager.common.dao.SourcesDao
import org.grove.metricsmanager.common.entity.Source
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SourcesService(
    private val sourcesDao: SourcesDao
) {

    fun getAllSources(): List<Source> {
        return runCatching {
            sourcesDao.getAllSources()
        }.getOrElse { throw DatabaseException("Details not provided", it) }
    }

    fun getSourceById(id: UUID): Source {
        return runCatching {
            sourcesDao.getSourceById(id)
        }.getOrElse {
            throw DatabaseException("Details not provided", it)
        } ?: throw SourceNotFoundException()
    }

    fun createSource(requestDto: CreateSourceRequestDto): UUID {
        val source = requestDto.toModel()

        runCatching {
            sourcesDao.createOrUpdateSource(source)
            source.id
        }.onFailure { throw DatabaseException("Details not provided", it) }

        return source.id
    }

    fun updateSource(requestDto: UpdateSourceRequestDto) {
        val source = requestDto.toModel()
        if (sourcesDao.getSourceById(source.id) == null) {
            throw SourceNotFoundException()
        }

        runCatching {
            sourcesDao.createOrUpdateSource(source)
        }.onFailure { throw DatabaseException("Details not provided", it) }
    }

    fun deleteSource(id: UUID) {
        if (sourcesDao.getSourceById(id) == null) {
            throw SourceNotFoundException()
        }

        runCatching {
            sourcesDao.deleteSource(id)
        }.onFailure { throw DatabaseException("Details not provided", it) }
    }
}
