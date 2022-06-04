CREATE TABLE shopping_list_items
(
    id               INTEGER      NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    modified_at      TIMESTAMP    NOT NULL,
    name             VARCHAR(255) NOT NULL,
    quantity         VARCHAR(255),
    sequence         INTEGER,
    deleted          BOOLEAN      NOT NULL,
    completed        BOOLEAN      NOT NULL,
    shopping_list_id INTEGER,
    receipt_id       INTEGER,
    CONSTRAINT pk_shopping_list_item_id PRIMARY KEY (id),
    CONSTRAINT fk_shopping_list_item_receipt_id FOREIGN KEY (receipt_id) REFERENCES receipts (id),
    CONSTRAINT fk_shopping_list_item_shopping_list_id FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists (id)
);

CREATE SEQUENCE seq_shopping_list_item_id INCREMENT 1 START 1;
ALTER SEQUENCE seq_shopping_list_item_id OWNED BY shopping_list_items.id;