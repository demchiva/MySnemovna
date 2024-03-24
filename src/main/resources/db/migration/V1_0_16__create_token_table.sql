CREATE TABLE IF NOT EXISTS sn_tokens (
   id BIGINT NOT NULL,
   user_id BIGINT NOT NULL,
   token VARCHAR(50) NOT NULL,
   token_type VARCHAR(100) NOT NULL,
   revoked INTEGER,
   expired INTEGER,
   PRIMARY KEY (id)
);