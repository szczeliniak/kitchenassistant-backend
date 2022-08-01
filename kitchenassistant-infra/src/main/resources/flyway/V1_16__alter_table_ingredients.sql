ALTER TABLE ingredients ADD COLUMN ingredient_group_id INTEGER;
ALTER TABLE ingredients ADD CONSTRAINT fk_ingredient_ingredient_group_id FOREIGN KEY (ingredient_group_id) REFERENCES ingredient_groups(id);

UPDATE ingredients SET ingredient_group_id = ingredient_group.id FROM (SELECT id, recipe_id from ingredient_groups) AS ingredient_group WHERE ingredients.recipe_id = ingredient_group.recipe_id;

ALTER TABLE ingredients DROP CONSTRAINT fk_ingredient_recipe_id;
ALTER TABLE ingredients DROP COLUMN recipe_id;