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
        String sql = "SELECT SUM(latest_prices.price * di.required_quantity) AS total_cost\n" +
                "FROM Dish_Ingredient di\n" +
                "JOIN (\n" +
                "    SELECT iph.id_ingredient, iph.price\n" +
                "    FROM Ingredient_Price_History iph\n" +
                "    WHERE iph.update_date = (\n" +
                "        SELECT MAX(update_date)\n" +
                "        FROM Ingredient_Price_History\n" +
                "        WHERE id_ingredient = iph.id_ingredient\n" +
                "        AND update_date <= ?\n" +
                "    )\n" +
                ") AS latest_prices ON di.id_ingredient = latest_prices.id_ingredient\n" +
                "WHERE di.id_dish = ?\n" +
                "GROUP BY di.id_dish;";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));  // Paramétrer la date
            stmt.setInt(2, this.id);  // Paramétrer l'ID du plat

            // Exécution de la requête
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalCost = rs.getDouble("total_cost");  // Récupérer le coût total
                    System.out.println("Coût total des ingrédients : " + totalCost);
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
    public Double getGrossMargin(LocalDate date) throws SQLException {
        double ingredientsCost = getIngredientsCost(date);  // Coût des ingrédients à la date spécifiée
        double margin = this.unitPrice - ingredientsCost;    // Calcul de la marge brute
        System.out.println("Coût des ingrédients à la date " + date + " : " + ingredientsCost);
        System.out.println("Marge brute : " + margin);
        return margin;
    }

    public Double getGrossMargin() throws SQLException {
        return getGrossMargin(LocalDate.now());
    }
}
