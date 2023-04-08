CREATE TABLE IF NOT EXISTS enrollment (
    id SERIAL NOT NULL PRIMARY KEY,
    person_id BIGINT NOT NULL,
    id_of BIGINT NOT NULL,
    type INTEGER NOT NULL,
    enrollment_from VARCHAR(50),
    enrollment_to VARCHAR(50),
    mandate_from DATE,
    mandate_to DATE
);