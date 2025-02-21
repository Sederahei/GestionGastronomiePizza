package org.alherendro.DAO;


import com.sun.jdi.connect.spi.Connection;
import org.alherendro.Etinty.Dish;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.IngredientQuantity;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DishDAO {
        private Connection connection;


        public DishDAO(Connection connection) {
            this.connection = connection;
        }

        public Dish getDishDAO(int dishId) throws SQLException {
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

    public abstract Dish finalize(int id) throws SQLException;
}

