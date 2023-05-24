create table company
(
    id               SERIAL PRIMARY KEY,
    name             varchar(1024)                       not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null
);

create table employee
(
    id               SERIAL PRIMARY KEY,
    company_fk       SERIAL                              not null,
    name             varchar(1024)                       not null,
    email            varchar(1024)                       not null,
    create_date      timestamp default CURRENT_TIMESTAMP not null,
    last_update_date timestamp default CURRENT_TIMESTAMP not null
);