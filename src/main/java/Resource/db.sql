-- 1. Création de l'utilisateur
CREATE USER lagastro_user WITH PASSWORD 'alherendro';


CREATE DATABASE lagastronomiepizza OWNER lagastro_user;

\c lagastronomiepizza;

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
    unit_price NUMERIC NOT NULL,
    unit unit_enum NOT NULL
);

CREATE TABLE dish (
    id_dish SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unit_price NUMERIC NOT NULL
);

CREATE TABLE dish_ingredient (
    id_dish INT REFERENCES dish(id_dish) ON DELETE CASCADE,
    id_ingredient INT REFERENCES ingredient(id_ingredient) ON DELETE CASCADE,
    required_quantity NUMERIC NOT NULL,
    unit unit_enum NOT NULL,
    PRIMARY KEY (id_dish, id_ingredient)
);

-- 5. Insertion des données de test
INSERT INTO dish (name, unit_price) VALUES ('Hot Dog', 15000);

INSERT INTO ingredient (name, update_datetime, unit_price, unit) VALUES
('Saucisse', '2025-01-01 00:00', 20, 'G'),
('Huile', '2025-01-01 00:00', 10000, 'L'),
('Oeuf', '2025-01-01 00:00', 1000, 'U'),
('Pain', '2025-01-01 00:00', 1000, 'U');

INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit) VALUES
(1, 1, 100, 'G'),
(1, 2, 0.15, 'L'),
(1, 3, 1, 'U'),
(1, 4, 1, 'U');

-- 6. Calcul du coût total des ingrédients du Hot Dog
SELECT d.name AS dish_name, 
       SUM(i.unit_price * di.required_quantity) AS total_ingredient_cost
FROM dish d
JOIN dish_ingredient di ON d.id_dish = di.id_dish
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE d.name = 'Hot Dog'
GROUP BY d.name;

-- 7. Filtrage : Ingrédients contenant "u" et prix <= 1000
SELECT * FROM ingredient
WHERE name ILIKE '%u%'
  AND unit_price <= 1000;

-- 8. Tri : Par nom ASC, prix DESC
SELECT * FROM ingredient
ORDER BY name ASC, unit_price DESC;

-- 9. Pagination : 1 élément par page, récupérer le 2ème élément (OFFSET 1)
SELECT * FROM ingredient
ORDER BY name ASC, unit_price DESC
LIMIT 1 OFFSET 1;

-- 10. Requêtes complémentaires pour la partie JAVA

-- Récupération des ingrédients d'un plat spécifique par ID (pour DishDAO)
SELECT i.id_ingredient, i.name, i.unit_price, i.unit, di.required_quantity
FROM dish_ingredient di
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE di.id_dish = 1;

-- Vérification du calcul via SQL (doit retourner 5500 pour Hot Dog)
SELECT d.name AS dish_name,
       SUM(i.unit_price * di.required_quantity) AS calculated_cost
FROM dish d
JOIN dish_ingredient di ON d.id_dish = di.id_dish
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE d.name = 'Hot Dog'
GROUP BY d.name;

-- Requêtes pour filtrer, trier et paginer pour la DAO Java
SELECT * FROM ingredient
WHERE name ILIKE '%u%'
  AND unit_price <= 1000
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

-- 5. Insertion des données de test
INSERT INTO dish (name, price) VALUES ('Hot Dog', 15000);

INSERT INTO ingredient (name, update_datetime, price, unit) VALUES
('Saucisse', '2025-01-01 00:00', 20, 'G'),
('Huile', '2025-01-01 00:00', 10000, 'L'),
('Oeuf', '2025-01-01 00:00', 1000, 'U'),
('Pain', '2025-01-01 00:00', 1000, 'U');

INSERT INTO dish_ingredient (id_dish, id_ingredient, required_quantity, unit) VALUES
(1, 1, 100, 'G'),
(1, 2, 0.15, 'L'),
(1, 3, 1, 'U'),
(1, 4, 1, 'U');

-- 6. Calcul du coût total des ingrédients du Hot Dog
SELECT d.name AS dish_name, 
       SUM(i.price * di.required_quantity) AS total_ingredient_cost
FROM dish d
JOIN dish_ingredient di ON d.id_dish = di.id_dish
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE d.name = 'Hot Dog'
GROUP BY d.name;

-- 7. Filtrage : Ingrédients contenant "u" et prix <= 1000
SELECT * FROM ingredient
WHERE name ILIKE '%u%'
  AND price <= 1000;

-- 8. Tri : Par nom ASC, prix DESC
SELECT * FROM ingredient
ORDER BY name ASC, price DESC;

-- 9. Pagination : 1 élément par page, récupérer le 2ème élément (OFFSET 1)
SELECT * FROM ingredient
ORDER BY name ASC, price DESC
LIMIT 1 OFFSET 1;

-- 10. Requêtes complémentaires pour la partie JAVA

-- Récupération des ingrédients d'un plat spécifique par ID (pour DishDAO)
SELECT i.id_ingredient, i.name, i.price, i.unit, di.required_quantity
FROM dish_ingredient di
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE di.id_dish = 1;

-- Vérification du calcul via SQL (doit retourner 5500 pour Hot Dog)
SELECT d.name AS dish_name,
       SUM(i.price * di.required_quantity) AS calculated_cost
FROM dish d
JOIN dish_ingredient di ON d.id_dish = di.id_dish
JOIN ingredient i ON di.id_ingredient = i.id_ingredient
WHERE d.name = 'Hot Dog'
GROUP BY d.name;

-- Requêtes pour filtrer, trier et paginer pour la DAO Java
SELECT * FROM ingredient
WHERE name ILIKE '%u%'
  AND price <= 1000
ORDER BY name ASC, price DESC
LIMIT 1 OFFSET 1;





















// 2. Classe Ingredi

// 3. Classe Dish.java
package com.lagastronomiepizza.model;

import java.util.List;

public class Dish {
    private int id;
    private String name;
    private double price;
    private List<IngredientQuantity> ingredients;

    public Dish(int id, String name, double price, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public double getIngredientCost() {
        return ingredients.stream()
                .mapToDouble(iq -> iq.getIngredient().getPrice() * iq.getQuantity())
                .sum();
    }

    // Getters et setters
}

// 4. Classe IngredientQuantity.java
package com.lagastronomiepizza.model;

public class IngredientQuantity {
    private Ingredient ingredient;
    private double quantity;

    public IngredientQuantity(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    // Getters et setters
}

// 5. DAO pour DishDAO.java
package com.lagastronomiepizza.dao;

import com.lagastronomiepizza.model.*;
import java.sql.*;
import java.util.*;

public class DishDAO {
    private Connection connection;

    public DishDAO(Connection connection) {
        this.connection = connection;
    }

    public Dish findDishById(int dishId) throws SQLException {
        String dishQuery = "SELECT * FROM dish WHERE id_dish = ?";
        String ingredientQuery = "SELECT i.id_ingredient, i.name, i.price, i.unit, di.required_quantity " +
                "FROM dish_ingredient di JOIN ingredient i ON di.id_ingredient = i.id_ingredient WHERE di.id_dish = ?";

        try (PreparedStatement dishStmt = connection.prepareStatement(dishQuery);
             PreparedStatement ingredientStmt = connection.prepareStatement(ingredientQuery)) {

            dishStmt.setInt(1, dishId);
            ResultSet dishRs = dishStmt.executeQuery();

            if (dishRs.next()) {
                String name = dishRs.getString("name");
                double price = dishRs.getDouble("price");
                List<IngredientQuantity> ingredientQuantities = new ArrayList<>();

                ingredientStmt.setInt(1, dishId);
                ResultSet ingRs = ingredientStmt.executeQuery();
                while (ingRs.next()) {
                    Ingredient ingredient = new Ingredient(
                            ingRs.getInt("id_ingredient"),
                            ingRs.getString("name"),
                            null,
                            ingRs.getDouble("price"),
                            Unit.valueOf(ingRs.getString("unit"))
                    );
                    double quantity = ingRs.getDouble("required_quantity");
                    ingredientQuantities.add(new IngredientQuantity(ingredient, quantity));
                }
                return new Dish(dishId, name, price, ingredientQuantities);
            }
        }
        return null;
    }
}

// 6. Classe DatabaseConnection.java
package com.lagastronomiepizza.utils;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/lagastronomiepizza";
    private static final String USER = "lagastro_user";
    private static final String PASSWORD = "secure_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// 7. Classe de test DishDAOTest.java
package com.lagastronomiepizza.test;

import com.lagastronomiepizza.dao.DishDAO;
import com.lagastronomiepizza.model.Dish;
import com.lagastronomiepizza.utils.DatabaseConnection;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class DishDAOTest {

    @Test
    public void testGetIngredientCost() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection()) {
            DishDAO dishDAO = new DishDAO(connection);
            Dish hotDog = dishDAO.findDishById(1);
            assertNotNull(hotDog);
            assertEquals(5500, hotDog.getIngredientCost(), 0.01);
        }
    }
}

// 8. StaticDataSource.java (Données statiques)
package com.lagastronomiepizza.datasource;

import com.lagastronomiepizza.model.*;
import java.time.LocalDateTime;
import java.util.*;

public class StaticDataSource {
    public static Dish getHotDogDish() {
        List<IngredientQuantity> ingredients = Arrays.asList(
                new IngredientQuantity(new Ingredient(1, "Saucisse", LocalDateTime.now(), 20, Unit.G), 100),
                new IngredientQuantity(new Ingredient(2, "Huile", LocalDateTime.now(), 10000, Unit.L), 0.15),
                new IngredientQuantity(new Ingredient(3, "Oeuf", LocalDateTime.now(), 1000, Unit.U), 1),
                new IngredientQuantity(new Ingredient(4, "Pain", LocalDateTime.now(), 1000, Unit.U), 1)
        );
        return new Dish(1, "Hot Dog", 15000, ingredients);
    }
}
