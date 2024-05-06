CREATE TABLE step_groups
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255),
    recipe_id   INTEGER,
    CONSTRAINT pk_step_group_id PRIMARY KEY (id),
    CONSTRAINT fk_step_group_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id)
);

CREATE SEQUENCE seq_step_group_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_step_group_id OWNED BY step_groups.id;

INSERT INTO step_groups (id, created_at, modified_at, name, recipe_id) SELECT nextval('seq_step_group_id'), NOW(), NOW(), null, recipe.id FROM recipes recipe;
ALTER TABLE steps ADD COLUMN step_group_id INTEGER;
ALTER TABLE steps ADD CONSTRAINT fk_step_step_group_id FOREIGN KEY (step_group_id) REFERENCES step_groups (id);
UPDATE steps SET step_group_id = step_groups.id FROM step_groups WHERE steps.recipe_id = step_groups.recipe_id;
ALTER TABLE steps DROP CONSTRAINT fk_step_recipe_id;
ALTER TABLE steps DROP COLUMN recipe_id;

ALTER TABLE ingredient_groups ALTER COLUMN name DROP NOT NULL;
ALTER TABLE ingredient_group_snapshots ALTER COLUMN name DROP NOT NULL;

DELETE FROM day_plan_recipe_snapshot_ids;
DELETE FROM step_snapshots;
DELETE FROM ingredient_snapshots;
DELETE FROM ingredient_group_snapshots;
DELETE FROM recipe_snapshots;
DELETE FROM day_plans;

CREATE TABLE step_group_snapshots
(
    id                      INTEGER      NOT NULL,
    created_at              TIMESTAMP    NOT NULL,
    modified_at             TIMESTAMP    NOT NULL,
    name                    VARCHAR(255),
    original_step_group_id  INTEGER,
    recipe_snapshot_id      INTEGER,
    CONSTRAINT pk_step_group_snapshot_id PRIMARY KEY (id),
    CONSTRAINT fk_step_group_snapshot_step_group_id FOREIGN KEY (original_step_group_id) REFERENCES step_groups (id),
    CONSTRAINT fk_step_group_snapshot_recipe_snapshot_id FOREIGN KEY (recipe_snapshot_id) REFERENCES recipe_snapshots (id)
);

CREATE SEQUENCE seq_step_group_snapshot_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_step_group_snapshot_id OWNED BY step_group_snapshots.id;

ALTER TABLE step_snapshots ADD COLUMN step_group_snapshot_id INTEGER;
ALTER TABLE step_snapshots ADD CONSTRAINT fk_step_snapshot_step_group_snapshot_id FOREIGN KEY (step_group_snapshot_id) REFERENCES step_group_snapshots (id);
ALTER TABLE step_snapshots DROP CONSTRAINT fk_step_snapshot_recipe_snapshot_id;
ALTER TABLE step_snapshots DROP COLUMN recipe_snapshot_id;