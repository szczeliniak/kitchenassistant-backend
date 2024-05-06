DROP SEQUENCE seq_file_id;
ALTER TABLE photos DROP CONSTRAINT fk_photo_file_id;
DROP TABLE files;
ALTER TABLE photos DROP COLUMN file_id;
ALTER TABLE photos
    ADD COLUMN user_id INTEGER;
ALTER TABLE photos
    ADD COLUMN name VARCHAR(255);
ALTER TABLE photos
    ADD COLUMN deleted BOOLEAN;
ALTER TABLE photos
    ADD CONSTRAINT fk_photos_user_id FOREIGN KEY (user_id) REFERENCES users (id)