CREATE TABLE IF NOT EXISTS meeting_state (
   meeting_id BIGINT NOT NULL PRIMARY KEY,
   state INTEGER,
   type INTEGER,
   meeting_begin_text VARCHAR(255),
   meeting_status_text VARCHAR(255),
   meeting_status_text_2 VARCHAR(255)
);