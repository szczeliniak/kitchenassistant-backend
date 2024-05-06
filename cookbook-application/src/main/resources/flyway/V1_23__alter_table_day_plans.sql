ALTER TABLE day_plans ADD COLUMN name VARCHAR(255) NOT NULL DEFAULT 'Default day plan name';
ALTER TABLE day_plans ADD COLUMN description VARCHAR(1000);
