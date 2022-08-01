CREATE TABLE ingredient_groups
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    name        VARCHAR(255) NOT NULL,
    recipe_id  INTEGER,
    CONSTRAINT pk_ingredient_group_id PRIMARY KEY (id),
    CONSTRAINT fk_ingredient_group_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id)
);

CREATE SEQUENCE seq_ingredient_group_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_ingredient_group_id OWNED BY ingredient_groups.id;

INSERT INTO ingredient_groups (id, created_at, modified_at, deleted, name, recipe_id) SELECT nextval('seq_ingredient_group_id'), NOW(), NOW(), false, 'default', recipe.id FROM recipes recipe;