package org.grove.metricsmanager.api.controller

import org.grove.metricsmanager.api.service.ConsumersService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/output/")
class ConsumersController(
    private val consumersService: ConsumersService
) {

    @GetMapping("/{id}")
    fun getConsumerById(
        @PathVariable("id") consumerId: UUID
    ) {

    }

    @PostMapping
    fun createConsumer() {

    }

    @PutMapping
    fun updateConsumer() {

    }

    @DeleteMapping("/{id}")
    fun deleteConsumer(
        @PathVariable("id") consumerId: UUID
    ) {
        
    }
}
