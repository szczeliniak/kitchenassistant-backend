CREATE TABLE authors
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    user_id     INTEGER      NOT NULL,
    CONSTRAINT pk_author_id PRIMARY KEY (id),
    CONSTRAINT fk_author_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE seq_author_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_author_id OWNED BY authors.id;