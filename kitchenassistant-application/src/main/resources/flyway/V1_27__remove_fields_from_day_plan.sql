ALTER TABLE day_plans DROP COLUMN name;
ALTER TABLE day_plans DROP COLUMN description;
DELETE FROM day_plans where date IS NULL;
ALTER TABLE day_plans ALTER COLUMN date SET NOT NULL;