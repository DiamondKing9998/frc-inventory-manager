ALTER TABLE items
ADD CONSTRAINT uq_items_name UNIQUE (name);

ALTER TABLE items
ADD CONSTRAINT uq_items_part_number UNIQUE (part_number);