ALTER TABLE recipe_snapshots DROP CONSTRAINT fk_recipe_snapshot_author_id;
ALTER TABLE recipe_snapshots DROP CONSTRAINT fk_recipe_snapshot_category_id;
ALTER TABLE recipe_snapshots DROP COLUMN category_id;
ALTER TABLE recipe_snapshots DROP COLUMN author_id;