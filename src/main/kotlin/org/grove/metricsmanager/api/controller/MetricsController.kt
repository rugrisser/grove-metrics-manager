package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.service.MetricsService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/metrics")
class MetricsController(
    private val metricsService: MetricsService
) {

    @GetMapping
    fun findMetric(
        @RequestParam(value = "id", required = false) metricId: UUID,
        @RequestParam(value = "sourceId", required = false) sourceId: UUID
    ) {

    }

    @PostMapping
    fun createMetric() {

    }

    @PutMapping
    fun updateMetric() {

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
    ) {

    }
}
