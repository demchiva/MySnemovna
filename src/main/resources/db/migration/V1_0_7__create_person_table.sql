CREATE TABLE IF NOT EXISTS person (
    id bigint NOT NULL PRIMARY KEY,
    title_before varchar(50),
    first_name varchar(255),
    last_name varchar(255),
    title_after varchar(255),
    birthdate date,
    sex varchar(1),
    updated_at date,
    date_of_death date
);