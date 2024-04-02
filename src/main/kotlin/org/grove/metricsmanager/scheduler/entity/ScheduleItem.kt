package org.grove.metricsmanager.scheduler.entity

import jakarta.persistence.*
import org.grove.metricsmanager.common.entity.Metric
import java.time.LocalDateTime

@Entity
@Table(name = "schedule")
data class ScheduleItem(
    @Id
    @JoinColumn(name = "m_id")
    @OneToOne(fetch = FetchType.EAGER)
    var metric: Metric = Metric(),

    @Column(name = "sch_planned_at")
    var plannedAt: LocalDateTime = ScheduleItemUtils.planScheduleItem(metric),

    @Column(name = "sch_status")
    var status: Status = Status.CREATED
) {

    enum class Status {
        CREATED, SENT, DONE;
    }
}
