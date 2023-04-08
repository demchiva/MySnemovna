CREATE TABLE IF NOT EXISTS function (
  id BIGINT NOT NULL PRIMARY KEY,
  organ_id BIGINT NOT NULL,
  function_type_id BIGINT,
  name_cz VARCHAR(255),
  priority INTEGER
);