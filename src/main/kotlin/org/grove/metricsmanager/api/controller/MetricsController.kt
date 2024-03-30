package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.dto.request.CreateMetricRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateMetricRequestDto
import org.grove.metricsmanager.api.dto.response.CreateMetricResponseDto
import org.grove.metricsmanager.common.entity.Metric
import org.grove.metricsmanager.common.service.MetricsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/metrics")
class MetricsController(
    private val metricsService: MetricsService
) {

    @GetMapping
    fun findMetric(
        @RequestParam(value = "sourceId", required = false) sourceId: UUID?
    ): List<Metric> {
        return when {
            sourceId != null -> metricsService.getMetricsBySourceId(sourceId)
            else -> metricsService.getAllMetrics()
        }
    }

    @GetMapping("/{id}")
    fun findMetricById(
        @PathVariable("id") metricId: UUID
    ): Metric {
        return metricsService.getMetricById(metricId)
    }

    @PostMapping
    fun createMetric(
        @RequestBody body: CreateMetricRequestDto
    ): CreateMetricResponseDto {
        return CreateMetricResponseDto(metricsService.createMetric(body))
    }

    @PutMapping
    fun updateMetric(
        @RequestBody body: UpdateMetricRequestDto
    ): ResponseEntity<Unit> {
        metricsService.updateMetric(body)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PatchMapping("/changeSource")
    fun changeSource() {

    }

    @PatchMapping("/changeConsumer")
    fun changeConsumer() {

    }

    @DeleteMapping("/{id}")
    fun deleteMetric(
        @PathVariable("id") metricId: UUID
    ): ResponseEntity<Unit> {
        metricsService.deleteMetricById(metricId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
