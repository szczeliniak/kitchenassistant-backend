CREATE TABLE recipes
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    source      VARCHAR(255),
    favorite   BOOLEAN      NOT NULL,
    user_id     INTEGER      NOT NULL,
    author_id   INTEGER,
    category_id INTEGER,
    deleted     BOOLEAN      NOT NULL,
    CONSTRAINT pk_recipe_id PRIMARY KEY (id),
    CONSTRAINT fk_recipe_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_recipe_author_id FOREIGN KEY (author_id) REFERENCES authors (id),
    CONSTRAINT fk_recipe_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE SEQUENCE seq_recipe_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_recipe_id OWNED BY recipes.id;