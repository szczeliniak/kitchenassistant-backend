CREATE TABLE photos
(
    id          INTEGER   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    modified_at TIMESTAMP NOT NULL,
    file_id     INTEGER   NOT NULL,
    receipt_id  INTEGER,
    CONSTRAINT pk_photo_id PRIMARY KEY (id),
    CONSTRAINT fk_photo_receipt_id FOREIGN KEY (receipt_id) REFERENCES receipts (id),
    CONSTRAINT fk_photo_file_id FOREIGN KEY (file_id) REFERENCES files (id)
);

CREATE SEQUENCE seq_photo_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_photo_id OWNED BY photos.id;