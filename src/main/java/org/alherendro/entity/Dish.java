package org.alherendro.entity;
import org.alherendro.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class Dish {
    private int id;
    private String name;
    private double unitPrice;
    private List<IngredientQuantity> ingredients;

    public Dish() {}

    public Dish(int id, String name, double unitPrice, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }

    public Dish(int id, String name, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Dish(int idDish, String name, double unitPrice) {
        this.id = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Dish(long dishId, String dishName, double dishPrice, List<IngredientQuantity> ingredients) {
        this.id = (int) dishId;
        this.name = dishName;
        this.unitPrice = dishPrice;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() { return unitPrice; }

    public List<IngredientQuantity> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }

    // Question N°4: Méthode mise à jour pour récupérer le coût des ingrédients à une date spécifique
    public double getIngredientsCost(LocalDate date) throws SQLException {
        double totalCost = 0.0;

        String sql = "SELECT i.unit_price AS ingredient_price " +
                "FROM Dish d " +
                "JOIN Dish_Ingredient di ON d.id_dish = di.id_dish " +
                "JOIN Ingredient_Price_History iph ON di.id_ingredient = iph.id_ingredient " +
                "WHERE d.id_dish = ? " +
                "AND iph.update_date <= ? " +
                "ORDER BY iph.update_date DESC LIMIT 1";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, this.id);  // Dish ID
            stmt.setDate(2, Date.valueOf(date));  // Date

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("ingredient_price");
                totalCost += price;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalCost;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Cette méthode permet de récupérer les coûts des ingrédients au jour d'aujourd'hui
    private double getIngredientCost() throws SQLException {
        return getIngredientsCost(LocalDate.now());
    }

    public Dish get() {
        return this;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
