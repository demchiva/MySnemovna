CREATE TABLE meeting_point (
   id SERIAL NOT NULL PRIMARY KEY,
   point_id BIGINT,
   agenda_type INT,
   meeting_id BIGINT,
   print_id BIGINT,
   type_id INT,
   point_number BIGINT,
   full_name VARCHAR(2000),
   name_suffix VARCHAR(255),
   note TEXT,
   state_id BIGINT,
   mode BIGINT,
   short_note VARCHAR(255),
   type INT,
   document_id BIGINT,
   short_name VARCHAR(255)
);