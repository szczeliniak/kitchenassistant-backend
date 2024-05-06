CREATE TABLE day_plans
(
    id           INTEGER      NOT NULL,
    user_id      INTEGER      NOT NULL,
    date         TIMESTAMP    NOT NULL,
    deleted      BOOLEAN      NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    modified_at  TIMESTAMP    NOT NULL
);

CREATE SEQUENCE seq_day_plan_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_day_plan_id OWNED BY day_plans.id;

CREATE TABLE day_plan_recipe_ids
(
    day_plan_id    INTEGER   NOT NULL,
    recipe_id     INTEGER NOT NULL
);