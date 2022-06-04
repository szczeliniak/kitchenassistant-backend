CREATE TABLE steps
(
    id          INTEGER      NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP    NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    sequence    INTEGER,
    deleted     BOOLEAN      NOT NULL,
    receipt_id  INTEGER,
    CONSTRAINT pk_step_id PRIMARY KEY (id),
    CONSTRAINT fk_step_receipt_id FOREIGN KEY (receipt_id) REFERENCES receipts (id)
);

CREATE SEQUENCE seq_step_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_step_id OWNED BY steps.id;