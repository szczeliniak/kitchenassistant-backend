CREATE TABLE users
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    email       VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE SEQUENCE seq_user_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_user_id OWNED BY users.id;