-- liquibase formatted sql

-- changeset sli:1
CREATE INDEX student_name_index ON student(name);

-- changeset sli:2
CREATE INDEX faculty_nameandcolor_index ON faculty(name, color);