ALTER TABLE day_plans ADD CONSTRAINT fk_day_plans_user_id FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX day_plans_user_id on day_plans (user_id);
CREATE INDEX day_plans_archived on day_plans (archived);
CREATE INDEX day_plans_archived_automatic_archiving on day_plans (automatic_archiving);
CREATE INDEX day_plans_date on day_plans (date);

ALTER TABLE authors ADD CONSTRAINT fk_authors_user_id FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX authors_user_id on authors (user_id);
CREATE INDEX authors_name on authors (name);

ALTER TABLE categories ADD CONSTRAINT fk_categories_user_id FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX categories_user_id on categories (user_id);
CREATE INDEX categories_deleted on categories (deleted);

CREATE INDEX photos_deleted on photos (deleted);

ALTER TABLE recipes ADD CONSTRAINT fk_recipes_user_id FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE recipes ADD CONSTRAINT fk_recipes_category_id FOREIGN KEY (user_id) REFERENCES categories (id);
CREATE INDEX recipes_deleted on recipes (deleted);
CREATE INDEX recipes_user_id on recipes (user_id);
CREATE INDEX recipes_name on recipes (name);
CREATE INDEX recipes_favorite on recipes (favorite);
CREATE INDEX recipes_category_id on recipes (category_id);

ALTER TABLE tags ADD CONSTRAINT fk_tags_user_id FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX tags_user_id on tags (user_id);
CREATE INDEX tags_name on tags (name);

ALTER TABLE shopping_lists ADD CONSTRAINT fk_shopping_lists_user_id FOREIGN KEY (user_id) REFERENCES users (id);
CREATE INDEX shopping_lists_deleted on shopping_lists (deleted);
CREATE INDEX shopping_lists_archived on shopping_lists (archived);
CREATE INDEX shopping_lists_name on shopping_lists (name);
CREATE INDEX shopping_lists_date on shopping_lists (date);
CREATE INDEX shopping_lists_automatic_archiving on shopping_lists (automatic_archiving);

ALTER TABLE shopping_list_items ADD CONSTRAINT fk_shopping_list_items_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id);
CREATE INDEX shopping_list_items_recipe_id on shopping_list_items (recipe_id);