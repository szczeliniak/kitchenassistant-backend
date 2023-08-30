CREATE TABLE recipe_snapshots
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    source      VARCHAR(255),
    author_id   INTEGER,
    category_id INTEGER,
    recipe_id   INTEGER,
    photo_name  VARCHAR(255),
    CONSTRAINT pk_recipe_snapshot_id PRIMARY KEY (id),
    CONSTRAINT fk_recipe_snapshot_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    CONSTRAINT fk_recipe_snapshot_author_id FOREIGN KEY (author_id) REFERENCES authors (id),
    CONSTRAINT fk_recipe_snapshot_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE SEQUENCE seq_recipe_snapshot_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_recipe_snapshot_id OWNED BY recipe_snapshots.id;

CREATE TABLE step_snapshots
(
    id                  INTEGER      NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    modified_at         TIMESTAMP    NOT NULL,
    photo_name          VARCHAR(255) NOT NULL,
    description         VARCHAR(255) NOT NULL,
    sequence            INTEGER,
    step_id             INTEGER,
    recipe_snapshot_id  INTEGER,
    CONSTRAINT pk_step_snapshot_id PRIMARY KEY (id),
    CONSTRAINT pk_step_snapshot_step_id FOREIGN KEY (step_id) REFERENCES steps (id),
    CONSTRAINT fk_step_snapshot_recipe_snapshot_id FOREIGN KEY (recipe_snapshot_id) REFERENCES recipe_snapshots (id)
);

CREATE SEQUENCE seq_step_snapshot_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_step_snapshot_id OWNED BY step_snapshots.id;

CREATE TABLE ingredient_group_snapshots
(
    id                   INTEGER      NOT NULL,
    created_at           TIMESTAMP    NOT NULL,
    modified_at          TIMESTAMP    NOT NULL,
    name                 VARCHAR(255) NOT NULL,
    ingredient_group_id  INTEGER,
    recipe_snapshot_id   INTEGER,
    CONSTRAINT pk_ingredient_group_snapshot_id PRIMARY KEY (id),
    CONSTRAINT fk_ingredient_group_snapshot_ingredient_group_id FOREIGN KEY (ingredient_group_id) REFERENCES ingredient_groups (id),
    CONSTRAINT fk_ingredient_group_snapshot_recipe_snapshot_id FOREIGN KEY (recipe_snapshot_id) REFERENCES recipe_snapshots (id)
);

CREATE SEQUENCE seq_ingredient_group_snapshot_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_ingredient_group_snapshot_id OWNED BY ingredient_group_snapshots.id;

CREATE TABLE ingredient_snapshots
(
    id                            INTEGER       NOT NULL,
    created_at                    TIMESTAMP     NOT NULL,
    modified_at                   TIMESTAMP     NOT NULL,
    name                          VARCHAR(255)  NOT NULL,
    quantity                      VARCHAR(255),
    ingredient_group_snapshot_id  INTEGER,
    ingredient_id                 INTEGER,
    CONSTRAINT pk_ingredient_snapshot_id PRIMARY KEY (id),
    CONSTRAINT fk_ingredient_snapshot_ingredient_group_snapshot_id FOREIGN KEY (ingredient_group_snapshot_id) REFERENCES ingredient_group_snapshots (id),
    CONSTRAINT fk_ingredient_snapshot_ingredient_id FOREIGN KEY (ingredient_id) REFERENCES ingredients (id)
);

CREATE SEQUENCE seq_ingredient_snapshot_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_ingredient_snapshot_id OWNED BY ingredient_snapshots.id;

ALTER TABLE day_plans ADD CONSTRAINT pk_day_plans_id PRIMARY KEY (id);

CREATE TABLE day_plan_recipe_snapshot_ids
(
    day_plan_id          INTEGER   NOT NULL,
    recipe_snapshot_id   INTEGER   NOT NULL,
    CONSTRAINT pk_day_plan_recipe_snapshot_ids_day_plan_id FOREIGN KEY (day_plan_id) REFERENCES day_plans (id),
    CONSTRAINT pk_day_plan_recipe_snapshot_ids_recipe_snapshot_id FOREIGN KEY (recipe_snapshot_id) REFERENCES recipe_snapshots (id)
);

DELETE FROM day_plan_recipe_ids;
DROP TABLE day_plan_recipe_ids;
DELETE FROM day_plans;