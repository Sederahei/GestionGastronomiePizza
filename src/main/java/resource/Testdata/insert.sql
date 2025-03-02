\c
lagastronomiepizza;
\d
Dish;
\d
Ingredient;
\d
Dish_Ingredient;


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


==> pour omelette
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


//pour   le mise a jour de prie de base et de la reorganisation  par Id :

UPDATE Dish
SET unit_price = 15000
WHERE name = 'Hot Dog';

SELECT *
FROM Dish
ORDER BY id_dish;

// pour le donne diplicate
DELETE
FROM ingredient
WHERE id_ingredient IN (SELECT id_ingredient
                        FROM ingredient
                        ORDER BY id_ingredient
                        OFFSET 4);

MISE A JOUR pour le istorique de prix(question 7)

UPDATE Ingredient
SET unit_price = 2500, update_datetime = '2025-03-01 12:00:00'
WHERE id_ingredient = 1;

Insertion de mise a joour :
          INSERT INTO Ingredient (id_ingredient, name, update_datetime, unit_price, unit)
VALUES
(1, 'Saucisse', '2025-03-01 00:00:00', 2500, 'G'),
(1, 'Saucisse', '2025-03-01 12:00:00', 3000, 'G');
