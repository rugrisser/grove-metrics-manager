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
    var id: UUID = UUID.randomUUID(),

    @Column(name = "m_key")
    var key: String = "",

    @Column(name = "m_update_frequency")
    var updateFrequency: Duration = Duration.ofMinutes(1),

    @ManyToOne
    @JoinColumn(name = "s_id")
    var source: Source = Source(),

    @ManyToOne
    @JoinColumn(name = "c_id")
    var consumer: Consumer = Consumer()
)

