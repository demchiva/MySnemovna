CREATE TABLE IF NOT EXISTS member_votes (
    member_id BIGINT NOT NULL,
    vote_id BIGINT NOT NULL,
    result VARCHAR(1),
    PRIMARY KEY (member_id, vote_id)
);