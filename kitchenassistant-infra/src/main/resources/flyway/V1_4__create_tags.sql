CREATE TABLE tags
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    user_id     INTEGER      NOT NULL,
    CONSTRAINT pk_tag_id PRIMARY KEY (id),
    CONSTRAINT fk_tag_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE seq_tag_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_tag_id OWNED BY tags.id;