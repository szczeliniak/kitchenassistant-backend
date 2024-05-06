ALTER TABLE recipes DROP CONSTRAINT fk_recipes_category_id;
ALTER TABLE recipes ADD CONSTRAINT fk_recipes_category_id FOREIGN KEY (category_id) REFERENCES categories (id);