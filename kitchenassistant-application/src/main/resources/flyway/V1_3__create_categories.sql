CREATE TABLE categories
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    user_id     INTEGER      NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    CONSTRAINT pk_category_id PRIMARY KEY (id),
    CONSTRAINT fk_category_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE seq_category_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_category_id OWNED BY categories.id;