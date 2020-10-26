CREATE TABLE cat
(
    cat_id BIGSERIAL PRIMARY KEY,
    name   TEXT NOT NULL,
    props  JSONB,
    slaves JSONB,
    toys   JSONB
);