CREATE TABLE receipts
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    source      VARCHAR(255),
    favorite   BOOLEAN      NOT NULL,
    user_id     INTEGER      NOT NULL,
    author_id   INTEGER,
    category_id INTEGER,
    deleted     BOOLEAN      NOT NULL,
    CONSTRAINT pk_receipt_id PRIMARY KEY (id),
    CONSTRAINT fk_receipt_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_receipt_author_id FOREIGN KEY (author_id) REFERENCES authors (id),
    CONSTRAINT fk_receipt_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE SEQUENCE seq_receipt_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_receipt_id OWNED BY receipts.id;