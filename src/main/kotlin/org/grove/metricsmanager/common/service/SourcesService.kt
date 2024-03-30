package org.grove.metricsmanager.common.service

import org.grove.metricsmanager.api.dto.request.CreateSourceRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateSourceRequestDto
import org.grove.metricsmanager.common.exception.SourceNotFoundException
import org.grove.metricsmanager.common.dao.SourcesDao
import org.grove.metricsmanager.common.entity.Source
import org.grove.metricsmanager.common.util.processDatabaseException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SourcesService(
    private val sourcesDao: SourcesDao
) {

    fun getAllSources(): List<Source> {
        return runCatching {
            sourcesDao.getAllSources()
        }.getOrElse(::processDatabaseException)
    }

    fun getSourceById(id: UUID): Source {
        return runCatching {
            sourcesDao.getSourceById(id)
        }.getOrElse(::processDatabaseException) ?: throw SourceNotFoundException()
    }

    fun createSource(requestDto: CreateSourceRequestDto): UUID {
        val source = requestDto.toModel()

        return runCatching {
            sourcesDao.createOrUpdateSource(source)
            source.id
        }.getOrElse(::processDatabaseException)
    }

    fun updateSource(requestDto: UpdateSourceRequestDto) {
        val source = requestDto.toModel()
        if (sourcesDao.getSourceById(source.id) == null) {
            throw SourceNotFoundException()
        }

        runCatching {
            sourcesDao.createOrUpdateSource(source)
        }.onFailure(::processDatabaseException)
    }

    fun deleteSource(id: UUID) {
        runCatching {
            sourcesDao.deleteSource(id)
        }.onFailure(::processDatabaseException)
    }
}
