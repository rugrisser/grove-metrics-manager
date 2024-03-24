package org.grove.metricsmanager.common.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "sources")
data class Source(
    @Id
    @Column(name = "s_id")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "s_name")
    var name: String = "",

    @Column(name = "s_link")
    var link: String = ""
)