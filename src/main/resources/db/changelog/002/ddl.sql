set search_path to public;

create type schedule_task_status as enum
    ( 'CREATED', 'SENT', 'DONE' );

create table schedule(
    m_id uuid not null,
    sch_planned_at timestamp not null,
    sch_status schedule_task_status not null default 'CREATED',

    constraint schedule_pk primary key (m_id),
    constraint metrics_fk foreign key (m_id) references metrics
);
