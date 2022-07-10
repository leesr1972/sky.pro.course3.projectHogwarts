ALTER TABLE student
    ADD CONSTRAINT age CHECK ( age >= 16 );

ALTER TABLE student
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);