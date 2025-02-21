CREATE TYPE unit AS ENUM ('G', 'L', 'U');

CREATE TABLE Ingredient
(
    id_ingredient   SERIAL PRIMARY KEY,
    name            VARCHAR(100)     NOT NULL,
    update_datetime TIMESTAMP        NOT NULL,
    unit_price      DOUBLE PRECISION NOT NULL,
    unit            unit             NOT NULL
);

CREATE TABLE Dish
(
    id_dish    SERIAL PRIMARY KEY,
    name       VARCHAR(100)     NOT NULL,
    unit_price DOUBLE PRECISION NOT NULL
);

CREATE TABLE Dish_Ingredient
(
    id_dish           INTEGER REFERENCES Dish (id_dish) ON DELETE CASCADE,
    id_ingredient     INTEGER REFERENCES Ingredient (id_ingredient) ON DELETE CASCADE,
    required_quantity DECIMAL(10, 2) NOT NULL,
    unit              unit           NOT NULL,
    PRIMARY KEY (id_dish, id_ingredient)
);