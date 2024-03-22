package org.grove.metricsmanager.common.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "sources")
data class Source(
    @Id
    @Column(name = "s_id")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "s_name")
    val name: String = "",

    @Column(name = "s_link")
    val link: String = ""
)
