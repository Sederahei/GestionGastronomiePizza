\c
lagastronomiepizza;

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



CREATE TABLE ingredient_quantity
(
    id                SERIAL PRIMARY KEY,
    id_ingredient     INTEGER        NOT NULL REFERENCES Ingredient (id_ingredient) ON DELETE CASCADE,
    required_quantity DECIMAL(10, 2) NOT NULL,
    unit              unit           NOT NULL
);


CREATE TABLE ingredient_prices
(
    id            SERIAL PRIMARY KEY,
    id_ingredient INTEGER          NOT NULL REFERENCES Ingredient (id_ingredient) ON DELETE CASCADE,
    price         DOUBLE PRECISION NOT NULL,
    price_date    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Ingredient_Price_History
(
    id            SERIAL PRIMARY KEY,
    id_ingredient INT            NOT NULL,
    price         DECIMAL(10, 2) NOT NULL,
    update_date   DATE           NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_ingredient) REFERENCES Ingredient (id_ingredient) ON DELETE CASCADE
);

//==> Pour voir les prix historiques des ingrédients et s’assurer qu’ils sont corrects,
                                                                    utilise cette requête :


SELECT * FROM Ingredient_Price_History ORDER BY id_ingredient, update_date
INSERT INTO Ingredient_Price_History (id_ingredient, price, update_date) VALUES (1, 200.0, '2025-01-01'), (1, 220.0, '2025-02-15'), (2, 50.0, '2025-01-10'), (2, 55.0, '2025-02-20');



CREATE TABLE stock_movement
(
    id                SERIAL PRIMARY KEY,
    id_ingredient     INTEGER                                                   NOT NULL REFERENCES Ingredient (id_ingredient) ON DELETE CASCADE,
    movement_type     VARCHAR(10) CHECK (movement_type IN ('ENTREE', 'SORTIE')) NOT NULL,
    quantity          DECIMAL(10, 2)                                            NOT NULL,
    unit              unit                                                      NOT NULL,
    movement_datetime TIMESTAMP                                                 NOT NULL DEFAULT CURRENT_TIMESTAMP
);

