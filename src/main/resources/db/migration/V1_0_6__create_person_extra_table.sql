CREATE TABLE IF NOT EXISTS person_extra (
  id SERIAL PRIMARY KEY,
  person_id BIGINT NOT NULL,
  organ_id BIGINT,
  type INTEGER,
  district BIGINT,
  party VARCHAR(255),
  external_id BIGINT
);