package org.grove.metricsmanager.api.dto.request

interface RequestModel<T> {

    fun toModel(): T
}