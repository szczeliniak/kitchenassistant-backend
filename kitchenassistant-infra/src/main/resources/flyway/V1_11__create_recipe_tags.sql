CREATE TABLE recipe_tags
(
    recipe_id INTEGER NOT NULL,
    tag_id     INTEGER NOT NULL,
    CONSTRAINT pk_recipe_tags PRIMARY KEY (recipe_id, tag_id),
    CONSTRAINT fk_recipe_tags_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    CONSTRAINT fk_recipe_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id)
);