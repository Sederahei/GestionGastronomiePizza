package org.alherendro.DAO;

import Resource.DataSource;
import org.alherendro.Etinty.Dish;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.IngredientQuantity;
import org.alherendro.Etinty.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {
    public Dish findById(int id) throws SQLException {
        String query = "SELECT d.id_dish, d.name, d.unit_price, " +
                "i.id_ingredient, i.name, i.update_datetime, i.unit_price, i.unit, di.required_quantity " +
                "FROM dish d " +
                "JOIN dish_ingredient di ON d.id_dish = di.id_dish " +
                "JOIN ingredient i ON i.id_ingredient = di.id_ingredient " +
                "WHERE d.id_dish = ?";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Dish dish = null;
            List<IngredientQuantity> ingredients = new ArrayList<>();
            while (rs.next()) {
                if (dish == null) {
                    dish = new Dish(
                            rs.getInt("id_dish"),
                            rs.getString("name"),
                            rs.getDouble("unit_price"),
                            ingredients
                    );
                }
                Ingredient ingredient = new Ingredient(
                        rs.getInt("id_ingredient"),
                        rs.getString("name"),
                        rs.getTimestamp("update_datetime").toLocalDateTime(),
                        rs.getDouble("unit_price"),
                        Unit.valueOf(rs.getString("unit"))
                );
                ingredients.add(new IngredientQuantity(ingredient, rs.getDouble("required_quantity")));
            }
            if (dish == null) {
                throw new SQLException("No dish found with ID: " + id);
            }
            return dish;
        }
    }
}
