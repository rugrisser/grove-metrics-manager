create table sources(
    s_id uuid,
    s_name varchar(255),
    s_link varchar(512),

    constraint sources_pk primary key (s_id)
);

create table metrics(
    m_id uuid,
    s_id uuid,
    m_key varchar(255),
    m_update_frequency interval,

    constraint metrics_pk primary key (m_id),
    constraint sources_pk foreign key (s_id) references sources
                    on delete cascade
                    on update cascade
);
