package org.grove.metricsmanager.common.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLIntervalSecondJdbcType
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "metrics")
data class Metric(
    @Id
    @Column(name = "m_id")
    var id: UUID = UUID.randomUUID(),

    @Column(name = "m_key")
    var key: String = "",

    @Column(
        name = "m_update_frequency",
        columnDefinition = "interval"
    )
    @JdbcType(PostgreSQLIntervalSecondJdbcType::class)
    var updateFrequency: Duration = Duration.ofMinutes(1),

    @Column(name = "m_updated_due")
    var updatedDue: LocalDateTime? = null,

    @Column(name = "m_ignore_until")
    var ignoreUntil: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "s_id")
    var source: Source? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_id")
    var consumer: Consumer? = null,
)

