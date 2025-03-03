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

    public Dish() {
    }

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

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public List<IngredientQuantity> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }
    // Question N°4: Méthode mise à jour pour récupérer le coût des ingrédients à une date spécifique

    public double getIngredientsCost(LocalDate date) throws SQLException {
        double totalCost = 0.0;

        // Requête SQL pour récupérer le coût des ingrédients à une date donnée
        String sql = "SELECT SUM(latest_prices.price * di.required_quantity) AS total_cost " +
                "FROM Dish d " +
                "JOIN Dish_Ingredient di ON d.id_dish = di.id_dish " +
                "JOIN ( " +
                "    SELECT DISTINCT ON (iph.id_ingredient) iph.id_ingredient, iph.price " +
                "    FROM Ingredient_Price_History iph " +
                "    WHERE iph.update_date <= ? " +
                "    ORDER BY iph.id_ingredient, iph.update_date DESC " +
                ") AS latest_prices ON di.id_ingredient = latest_prices.id_ingredient " +
                "WHERE d.id_dish = ? " +
                "GROUP BY d.id_dish;";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));  // Paramétrer la date d'ingrédient
            stmt.setInt(2, this.id);  // ID du plat

            // Exécution de la requête
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalCost = rs.getDouble("total_cost");  // Récupérer le coût total
                } else {
                    System.out.println("Aucun coût trouvé pour les ingrédients à cette date.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du coût des ingrédients", e);  // Gestion des erreurs SQL
        }

        return totalCost;
    }

    public Double getGrossMargin() throws SQLException {
        return getGrossMargin(LocalDate.now());  // Par défaut, calculer la marge brute avec la date d'aujourd'hui
    }

    public Double getGrossMargin(LocalDate date) throws SQLException {
        // Calcul du coût des ingrédients à une date donnée
        double ingredientsCost = getIngredientsCost(date);

        // Retourner la marge brute : prix de vente unitaire - coût des ingrédients
        return this.unitPrice - ingredientsCost;

    }
}