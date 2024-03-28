package org.grove.metricsmanager.common.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
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
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var type: ConsumerType = ConsumerType.PROMETHEUS,

    @OneToMany(
        targetEntity = Property::class,
        mappedBy = "consumerId",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER
    )
    var properties: MutableSet<Property> = mutableSetOf()
) {

    @Entity
    @Table(name = "consumer_properties")
    data class Property(
        @Id
        @Column(name = "c_id")
        @JsonIgnore
        var consumerId: UUID = UUID.randomUUID(),

        @Id
        @Column(name = "cp_id")
        var id: UUID = UUID.randomUUID(),

        @Column(name = "cp_key")
        var key: String = "",

        @Column(name = "cp_value")
        var value: String = ""
    )
}
