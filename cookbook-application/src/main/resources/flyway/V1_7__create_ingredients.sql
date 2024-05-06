CREATE TABLE ingredients
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    quantity    VARCHAR(255) NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    recipe_id  INTEGER,
    CONSTRAINT pk_ingredient_id PRIMARY KEY (id),
    CONSTRAINT fk_ingredient_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id)
);

CREATE SEQUENCE seq_ingredient_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_ingredient_id OWNED BY ingredients.id;