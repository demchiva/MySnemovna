CREATE TABLE IF NOT EXISTS organ_type (
    id INTEGER NOT NULL,
    parent_id INTEGER,
    name_cz VARCHAR,
    name_en VARCHAR,
    common_type INTEGER,
    priority INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) references organ_type(id)
)