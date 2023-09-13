ALTER TABLE recipe_snapshots DROP CONSTRAINT fk_recipe_snapshot_recipe_id;
ALTER TABLE recipe_snapshots DROP COLUMN recipe_id;
ALTER TABLE recipe_snapshots ADD COLUMN original_recipe_id INTEGER;

ALTER TABLE step_snapshots DROP CONSTRAINT pk_step_snapshot_step_id;
ALTER TABLE step_snapshots DROP COLUMN step_id;
ALTER TABLE step_snapshots ADD COLUMN original_step_id INTEGER;

ALTER TABLE ingredient_group_snapshots DROP CONSTRAINT fk_ingredient_group_snapshot_ingredient_group_id;
ALTER TABLE ingredient_group_snapshots DROP COLUMN ingredient_group_id;
ALTER TABLE ingredient_group_snapshots ADD COLUMN original_ingredient_group_id INTEGER;

ALTER TABLE ingredient_snapshots DROP CONSTRAINT fk_ingredient_snapshot_ingredient_id;
ALTER TABLE ingredient_snapshots DROP COLUMN ingredient_id;
ALTER TABLE ingredient_snapshots ADD COLUMN original_ingredient_id INTEGER;