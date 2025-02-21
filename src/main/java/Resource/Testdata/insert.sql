

INSERT INTO dish (name, price)
VALUES ('Hot Dog', 15000);


INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit)
VALUES (1, 1, 100, 'G'),
       (1, 2, 0.15, 'L'),
       (1, 3, 1, 'U'),
       (1, 4, 1, 'U');




INSERT INTO ingredient (name, update_datetime, price, unit)
VALUES ('Saucisse', '2025-01-01 00:00', 20, 'G'),
       ('Huile', '2025-01-01 00:00', 10000, 'L'),
       ('Oeuf', '2025-01-01 00:00', 1000, 'U'),
       ('Pain', '2025-01-01 00:00', 1000, 'U');