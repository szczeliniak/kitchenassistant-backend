CREATE TABLE recipes_photos
(
    recipe_id INTEGER NOT NULL,
    photo_id  INTEGER NOT NULL,
    CONSTRAINT pk_recipes_photos PRIMARY KEY (recipe_id, photo_id),
    CONSTRAINT fk_recipes_photos_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes (id),
    CONSTRAINT fk_recipes_photos_photo_id FOREIGN KEY (photo_id) REFERENCES photos (id)
);

DELETE FROM photos;
ALTER TABLE photos DROP COLUMN recipe_id;