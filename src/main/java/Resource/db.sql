
-- 1. Afficher tous les ingreédients



-- database de jva --
INSERT INTO dish (name, unit_price)
VALUES ('Hot Dog', 15000);



-- 6. Calcul du coût total des ingrédients du Hot Dog
SELECT d.name                                   AS dish_name,
       SUM(i.unit_price * di.required_quantity) AS total_ingredient_cost
FROM dish d
         JOIN dish_ingredient di ON d.id_dish = di.id_dish
         JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE d.name = 'Hot Dog'
GROUP BY d.name;

-- 7. Filtrage : Ingrédients contenant "u" et prix <= 1000
SELECT *
FROM ingredient
WHERE name ILIKE '%u%'
  AND unit_price <= 1000;

-- 8. Tri : Par nom ASC, prix DESC
SELECT *
FROM ingredient
ORDER BY name ASC, unit_price DESC;

-- 9. Pagination : 1 élément par page, récupérer le 2ème élément (OFFSET 1)
SELECT *
FROM ingredient
ORDER BY name ASC, unit_price DESC
LIMIT 1 OFFSET 1;



































 -- 1. Création de l'utilisateur
CREATE USER lagastro_user WITH PASSWORD 'secure_password';

-- 2. Création de la base de données et attribution des privilèges
CREATE DATABASE lagastronomiepizza OWNER lagastro_user;

\\c lagastronomiepizza;

GRANT ALL PRIVILEGES ON DATABASE lagastronomiepizza TO lagastro_user;

-- 3. Création du type ENUM pour les unités
CREATE TYPE unit_enum AS ENUM ('G', 'L', 'U');

-- 4. Création des tables
CREATE TABLE ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    update_datetime TIMESTAMP NOT NULL,
    price NUMERIC NOT NULL,
    unit unit_enum NOT NULL
);

CREATE TABLE dish (
    id_dish SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC NOT NULL
);

CREATE TABLE dish_ingredient (
    id_dish INT REFERENCES dish(id_dish) ON DELETE CASCADE,
    id_ingredient INT REFERENCES ingredient(id_ingredient) ON DELETE CASCADE,
    required_quantity NUMERIC NOT NULL,
    unit unit_enum NOT NULL,
    PRIMARY KEY (id_dish, id_ingredient)
);
