set search_path to public;

-- sources

create table sources(
    s_id uuid not null,
    s_name varchar(255) not null,
    s_link varchar(512) unique not null,

    constraint sources_pk primary key (s_id)
);

-- consumers

create type consumer_type as enum
    ('PROMETHEUS', 'PROMETHEUS_PUSHGATEWAY', 'CLICKHOUSE');

create table consumers(
    c_id uuid not null,
    c_name varchar(255) not null,
    c_type consumer_type not null default 'PROMETHEUS',

    constraint consumers_pk primary key (c_id)
);

create table consumer_properties(
    c_id uuid,
    cp_id uuid not null,
    cp_key varchar(255) not null ,
    cp_value varchar(255) not null,

    constraint consumer_properties_pk primary key (c_id, cp_id),
    constraint consumers_fk foreign key (c_id) references consumers
                                on delete cascade
                                on update cascade
);

-- metrics

create table metrics(
    m_id uuid not null,
    s_id uuid,
    c_id uuid,
    m_key varchar(255) not null ,
    m_update_frequency interval
        not null
        default interval '1 minute'
                    check ( m_update_frequency >= interval '1 minute' ),
    m_updated_due timestamp default null,
    m_ignore_until timestamp default null,

    constraint metrics_pk primary key (m_id),
    constraint sources_pk foreign key (s_id) references sources
                    on delete set null
                    on update cascade,
    constraint consumers_pk foreign key (c_id) references consumers
                    on delete set null
                    on update cascade
);
