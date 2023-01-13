create table if not exists Point
(
    id            serial  not null,
    dateTime      timestamp,
    executionTime float8  not null,
    message       varchar(255),
    r             float8  not null,
    res           boolean not null,
    x             float8  not null,
    y             float8  not null,
    user_id       int8,
    primary key (id)
);

create table if not exists "User"
(
    user_id       bigserial not null,
    authenticated boolean   not null,
    password      varchar(255),
    username      varchar(255),
    primary key (user_id)
)