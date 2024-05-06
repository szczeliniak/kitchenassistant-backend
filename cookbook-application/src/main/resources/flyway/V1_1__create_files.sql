CREATE TABLE files
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    deleted     BOOLEAN      NOT NULL,
    name        VARCHAR(255) NOT NULL,
    user_id     INTEGER      NOT NULL,
    CONSTRAINT pk_file_id PRIMARY KEY (id),
    CONSTRAINT fk_file_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE seq_file_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_file_id OWNED BY files.id;