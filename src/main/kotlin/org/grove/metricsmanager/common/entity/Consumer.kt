package org.grove.metricsmanager.common.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "consumers")
data class Consumer(
    @Id
    @Column(name = "c_id")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "c_name")
    var name: String = "",

    @Column(name = "c_type")
    var type: Type = Type.PROMETHEUS,

    @OneToMany(targetEntity = Property::class)
    var properties: Set<Property> = setOf()
) {

    enum class Type(private val dbName: String) {
        PROMETHEUS("prometheus"),
        CLICKHOUSE("clickhouse"),
        PROMETHEUS_PUSHGATEWAY("prometheus_pushgateway");

        override fun toString() = dbName
    }

    @Entity
    @Table(name = "consumer_properties")
    data class Property(
        @Id
        @Column(name = "cp_id")
        var id: UUID = UUID.randomUUID(),

        @Column(name = "cp_key")
        var key: String = "",

        @Column(name = "cp_value")
        var value: String = ""
    )
}
