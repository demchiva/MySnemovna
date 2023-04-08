CREATE TABLE IF NOT EXISTS meeting (
   id BIGINT NOT NULL,
   agenda_type INTEGER NOT NULL,
   organ_id BIGINT NOT NULL,
   meeting_number BIGINT NOT NULL,
   date_from VARCHAR(50),
   date_to VARCHAR(50),
   updated_at VARCHAR(50),
   PRIMARY KEY (id, agenda_type)
);