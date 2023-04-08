CREATE TABLE IF NOT EXISTS organ_type (
    id INTEGER NOT NULL PRIMARY KEY,
    parent_id INTEGER,
    name_cz VARCHAR,
    name_en VARCHAR,
    common_type INTEGER,
    priority INTEGER
)