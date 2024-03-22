package org.grove.metricsmanager.common.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Duration
import java.util.UUID

@Entity
@Table(name = "metrics")
data class Metric(
    @Id
    @Column(name = "m_id")
    val id: UUID = UUID.randomUUID(),

    @Column(name = "m_key")
    val key: String = "",

    @Column(name = "m_update_frequency")
    val updateFrequency: Duration = Duration.ofMinutes(1),

    @ManyToOne
    @JoinColumn(name = "s_id")
    val source: Source = Source()
)

