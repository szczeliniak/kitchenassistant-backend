CREATE TABLE shopping_lists
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    date        DATE,
    sequence    INTEGER,
    deleted     BOOLEAN      NOT NULL,
    archived    BOOLEAN      NOT NULL,
    user_id     INTEGER      NOT NULL,
    CONSTRAINT pk_shopping_list_id PRIMARY KEY (id),
    CONSTRAINT fk_shopping_list_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE SEQUENCE seq_shopping_list_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_shopping_list_id OWNED BY shopping_lists.id;