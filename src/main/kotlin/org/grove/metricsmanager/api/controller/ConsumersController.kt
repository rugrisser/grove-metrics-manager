package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.dto.request.CreateConsumerRequestDto
import org.grove.metricsmanager.api.dto.request.UpdateConsumerRequestDto
import org.grove.metricsmanager.api.dto.response.CreateConsumerResponseDto
import org.grove.metricsmanager.common.service.ConsumersService
import org.grove.metricsmanager.common.entity.Consumer
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
@RequestMapping("/api/output")
class ConsumersController(
    private val consumersService: ConsumersService
) {

    @GetMapping("/all")
    fun getAllConsumers(): List<Consumer> {
        return consumersService.getAllConsumers()
    }

    @GetMapping("/{id}")
    fun getConsumerById(
        @PathVariable("id") consumerId: UUID
    ): Consumer {
        return consumersService.getConsumerById(consumerId)
    }

    @PostMapping
    fun createConsumer(
        @RequestBody body: CreateConsumerRequestDto
    ): CreateConsumerResponseDto {
        return CreateConsumerResponseDto(consumersService.createConsumer(body))
    }

    @PutMapping
    fun updateConsumer(
        @RequestBody body: UpdateConsumerRequestDto
    ): ResponseEntity<Unit> {
        consumersService.updateConsumer(body)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @DeleteMapping("/{id}")
    fun deleteConsumer(
        @PathVariable("id") consumerId: UUID
    ): ResponseEntity<Unit> {
        consumersService.deleteConsumer(consumerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
