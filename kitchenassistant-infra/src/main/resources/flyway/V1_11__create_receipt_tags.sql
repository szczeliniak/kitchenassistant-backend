CREATE TABLE receipt_tags
(
    receipt_id INTEGER NOT NULL,
    tag_id     INTEGER NOT NULL,
    CONSTRAINT pk_receipt_tags PRIMARY KEY (receipt_id, tag_id),
    CONSTRAINT fk_receipt_tags_receipt_id FOREIGN KEY (receipt_id) REFERENCES receipts (id),
    CONSTRAINT fk_receipt_tags_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id)
);