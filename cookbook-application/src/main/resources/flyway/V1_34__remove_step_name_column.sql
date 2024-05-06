UPDATE steps SET description = CONCAT(name, '. ', description);
ALTER TABLE steps DROP COLUMN name;