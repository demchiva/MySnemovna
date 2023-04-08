CREATE TABLE IF NOT EXISTS organ (
   id BIGINT NOT NULL PRIMARY KEY,
   parent_id BIGINT,
   organ_type_id BIGINT,
   short_name VARCHAR(255),
   name_cz VARCHAR(500),
   name_en VARCHAR(500),
   date_from DATE,
   date_to DATE,
   priority INTEGER,
   cl_organ_base INTEGER
);