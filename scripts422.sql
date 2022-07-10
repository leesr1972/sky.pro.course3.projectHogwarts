CREATE TABLE people
(
    id           SERIAL,
    name         VARCHAR(20),
    age          INTEGER,
    driveLicense BOOLEAN
);

ALTER TABLE people
    ADD car_id INTEGER REFERENCES cars(id);

ALTER TABLE people
    ADD PRIMARY KEY (id);

INSERT INTO people
VALUES (1, 'Ivan', 20, true, 1),
       (2, 'Ira', 18, false, 1),
       (3, 'Slava', 50, true, 2),
       (4, 'Marina', 43, false, 2);

CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(20),
    model VARCHAR(20),
    price INTEGER
);

INSERT INTO cars
VALUES (1, 'BMW', 'X6', 10000000),
       (2, 'LADA', '2106', 50000);

SELECT people.name, cars.brand, cars.model
FROM people
INNER JOIN cars ON people.car_id = cars.id;