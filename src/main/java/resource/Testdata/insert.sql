\c lagastronomiepizza;
\d Dish;
\d Ingredient;
\d Dish_Ingredient;


INSERT INTO Ingredient (name, update_datetime, unit_price, unit)
VALUES ('Saucisse', '2025-01-01 00:00:00', 20, 'G'),
       ('Huile', '2025-01-01 00:00:00', 10000, 'L'),
       ('Oeuf', '2025-01-01 00:00:00', 1000, 'U'),
       ('Pain', '2025-01-01 00:00:00', 1000, 'U');


INSERT INTO Ingredient (name, update_datetime, unit_price, unit)
VALUES ('Oeuf', NOW(), 1000, 'U'),
       ('Sel', NOW(), 500, 'G'),
       ('Poivre', NOW(), 2000, 'G'),
       ('Beurre', NOW(), 12000, 'G'),
       ('Lait', NOW(), 1500, 'L'),
       ('Fromage', NOW(), 8000, 'G');



INSERT INTO Dish (id_dish, name, unit_price)
VALUES (1, 'Hot dog', 15000);

INSERT INTO Dish_Ingredient (id_dish, id_ingredient, required_quantity, unit)
VALUES (1, 1, 100, 'G'),
       (1, 2, 0.15, 'L'),
       (1, 3, 1, 'U'),
       (1, 4, 1, 'U');



INSERT INTO Dish_Ingredient (id_dish, id_ingredient, required_quantity, unit)
SELECT d.id_dish, i.id_ingredient, ing.quantity, ing.unit::unit  -- Casting explicite ici
FROM Dish d
         JOIN Ingredient i ON i.name IN ('Oeuf', 'Sel', 'Poivre', 'Beurre', 'Lait', 'Fromage')
         JOIN (
    VALUES
        ('Oeuf', 2, 'U'),
        ('Sel', 2, 'G'),
        ('Poivre', 1, 'G'),
        ('Beurre', 10, 'G'),
        ('Lait', 0.1, 'L'),
        ('Fromage', 20, 'G')
) AS ing(name, quantity, unit) ON ing.name = i.name
WHERE d.name = 'Omelette';



INSERT INTO ingredient_quantity (id_ingredient, required_quantity, unit)
VALUES (1, 100.00, 'G'),
       (2, 0.15, 'L'),
       (3, 1.00, 'U'),
       (4, 1.00, 'U');



UPDATE Dish
SET unit_price = 15000
WHERE name = 'Hot Dog';

SELECT *
FROM Dish
ORDER BY id_dish;


DELETE
FROM ingredient
WHERE id_ingredient IN (SELECT id_ingredient
                        FROM ingredient
                        ORDER BY id_ingredient
                        OFFSET 4);



UPDATE Ingredient
SET unit_price = 2500, update_datetime = '2025-03-01 12:00:00'
WHERE id_ingredient = 1;


          INSERT INTO Ingredient (id_ingredient, name, update_datetime, unit_price, unit)
VALUES
(9, 'Tsaramaso', '2025-03-01 00:00:00', 2500, 'G'),
(1, 'Saucisse', '2025-03-01 12:00:00', 3000, 'G');

     /*    ==> commande pour metre l id recomnce en 1*/
/*ALTER SEQUENCE public.ingredient_price_history_id_seq RESTART WITH 1;*/


INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime)
VALUES

    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Saucisse'), 'IN', 10000, 'G', '2025-02-01 08:00:00'),


    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Huile'), 'IN', 20, 'L', '2025-02-01 08:00:00'),


    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Oeuf'), 'IN', 100, 'U', '2025-02-01 08:00:00'),


    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Pain'), 'IN', 50, 'U', '2025-02-01 08:00:00');




 /* manova ny second ho 06 chiffre */
ALTER TABLE stock_movement ALTER COLUMN movement_datetime TYPE TIMESTAMP(6);



INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime)
VALUES
    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Oeuf'), 'IN', 100, 'U', '2025-02-01 08:00:00'),

    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Pain'), 'IN', 50, 'U', '2025-02-01 08:00:00'),

    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Saucisse'), 'IN', 10000, 'G', '2025-02-01 08:00:00'),

    ((SELECT id_ingredient FROM Ingredient WHERE name = 'Huile'), 'IN', 20, 'L', '2025-02-01 08:00:00');

/* Ajout d'un index pour optimiser les recherches*/

CREATE INDEX idx_stock_movement_date ON stock_movement(movement_datetime);
CREATE INDEX idx_stock_movement_ingredient ON stock_movement(id_ingredient);
/*  Récupérer le stock disponible à une date donnée*/

SELECT id_ingredient,
       SUM(CASE WHEN movement_type = 'IN' THEN quantity ELSE -quantity END) AS stock_disponible
FROM stock_movement
WHERE movement_datetime <= '2025-03-06 23:59:59'
GROUP BY id_ingredient;

INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime)
VALUES (1, 'OUT', 10, 'U', '2025-02-02 10:00:00');

INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime)
VALUES (1, 'OUT', 10, 'U', '2025-02-03 15:00:00');

INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime)
VALUES (2, 'OUT', 20, 'U', '2025-02-05 16:00:00');