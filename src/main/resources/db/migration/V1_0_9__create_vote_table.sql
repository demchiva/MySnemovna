CREATE TABLE IF NOT EXISTS vote (
    id BIGINT NOT NULL PRIMARY KEY,
    organ_id BIGINT,
    meeting_number BIGINT,
    vote_number BIGINT,
    point_number BIGINT,
    date DATE,
    time TIME,
    aye INTEGER,
    no INTEGER,
    abstained INTEGER,
    was_not_on_vote INTEGER,
    logged_in INTEGER,
    quorum INTEGER,
    type VARCHAR(1),
    result VARCHAR(1),
    long_name VARCHAR(255),
    short_name VARCHAR(255)
);