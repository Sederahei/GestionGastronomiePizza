CREATE USER lagastro_user WITH PASSWORD 'alherendro';


CREATE DATABASE lagastronomiepizza OWNER lagastro_user;

GRANT ALL PRIVILEGES ON DATABASE lagastronomiepizza TO lagastro_user;


CREATE TYPE unit_enum AS ENUM ('G', 'L', 'U');


CREATE TABLE ingredient
(
    id_ingredient   SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    update_datetime TIMESTAMP    NOT NULL,
    unit_price      NUMERIC      NOT NULL,
    unit            unit_enum    NOT NULL
);

CREATE TABLE dish
(
    id_dish    SERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    unit_price NUMERIC      NOT NULL
);

CREATE TABLE dish_ingredient
(
    id_dish           INT REFERENCES dish (id_dish) ON DELETE CASCADE,
    id_ingredient     INT REFERENCES ingredient (id_ingredient) ON DELETE CASCADE,
    required_quantity NUMERIC   NOT NULL,
    unit              unit_enum NOT NULL,
    PRIMARY KEY (id_dish, id_ingredient)
);