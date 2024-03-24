package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.dto.request.CreateSourceRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateSourceRequestDto
import org.grove.metricsmanager.api.dto.response.CreateSourceResponseDto
import org.grove.metricsmanager.api.service.SourcesService
import org.grove.metricsmanager.common.entity.Source
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/sources")
class SourcesController(
    private val sourcesService: SourcesService
) {

    @GetMapping("/all")
    fun getAllSources(): List<Source> {
        return sourcesService.getAllSources()
    }

    @GetMapping("/{id}")
    fun getSourceById(
        @PathVariable("id") id: UUID
    ): Source {
        return sourcesService.getSourceById(id)
    }

    @PostMapping
    fun createSource(
        @RequestBody body: CreateSourceRequestDto
    ): CreateSourceResponseDto {
        return CreateSourceResponseDto(sourcesService.createSource(body))
    }

    @PutMapping
    fun updateSource(
        @RequestBody body: UpdateSourceRequestDto
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/{id}")
    fun deleteSource(
        @PathVariable("id") id: UUID
    ): ResponseEntity<Unit> {
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
