\c lagastronomiepizza;
\d Dish;
\d Ingredient;
\d Dish_Ingredient;


INSERT INTO Ingredient (name, update_datetime, unit_price, unit)
VALUES ('Saucisse', '2025-01-01 00:00:00', 20, 'G'),
       ('Huile', '2025-01-01 00:00:00', 10000, 'L'),
       ('Oeuf', '2025-01-01 00:00:00', 1000, 'U'),
       ('Pain', '2025-01-01 00:00:00', 1000, 'U');



INSERT INTO Dish (id_dish, name, unit_price) VALUES (1, 'Hot dog', 15000);

INSERT INTO Dish_Ingredient (id_dish, id_ingredient, required_quantity, unit)
VALUES (1, 1, 100, 'G'),
       (1, 2, 0.15, 'L'),
       (1, 3, 1, 'U'),
       (1, 4, 1, 'U');


INSERT INTO ingredient_quantity (id_ingredient, required_quantity, unit)
VALUES
    (1, 100.00, 'G'),
    (2, 0.15, 'L'),
    (3, 1.00, 'U'),
    (4, 1.00, 'U');


//pour   le mise a jour de prie de base et de la reorganisation  par Id :

UPDATE Dish
SET unit_price = 15000
WHERE name = 'Hot Dog';

SELECT * FROM Dish
ORDER BY id_dish;
