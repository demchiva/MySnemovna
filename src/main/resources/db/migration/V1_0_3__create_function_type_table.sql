CREATE TABLE IF NOT EXISTS function_type (
   id BIGINT NOT NULL PRIMARY KEY,
   organ_type_id BIGINT,
   name_cz VARCHAR(255),
   name_en VARCHAR(255),
   priority INTEGER,
   common_type BIGINT
);