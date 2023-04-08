CREATE TABLE IF NOT EXISTS member (
    id BIGINT NOT NULL PRIMARY KEY,
    person_id BIGINT,
    region_id BIGINT,
    party_id BIGINT,
    period_id BIGINT,
    web VARCHAR(255),
    street VARCHAR(255),
    municipality VARCHAR(255),
    zip VARCHAR(10),
    email VARCHAR(255),
    phone VARCHAR(30),
    fax VARCHAR(100),
    psp_phone VARCHAR(30),
    facebook VARCHAR(255),
    photo BOOLEAN
);