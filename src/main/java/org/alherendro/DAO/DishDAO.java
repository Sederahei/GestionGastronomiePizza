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

public class DishDAO implements CrudOperations <Dish> {


    public Dish findDishById(long id) throws SQLException {

    String required =
            """
                SELECT 
                    d.id_dish, d.name, d.unit_price, 
                    i.id_ingredient, i.name, i.update_datetime, i.unit_price, 
                    i.unit, di.required_quantity  
                FROM dish d 
                JOIN dish_ingredient di ON d.id_dish = di.id_dish 
                JOIN ingredient i ON i.id_ingredient = di.id_ingredient 
                WHERE d.id_dish = ?
            """;
    try(Connection conn = DataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(required)) {
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        List<IngredientQuantity> ingredients = new ArrayList<>();
        while (rs.next()) {
            Ingredient ingredient = new Ingredient(
                    rs.getInt("id_ingredient"),
                    rs.getString("name"),
                    rs.getTimestamp("update_datetime").toLocalDateTime(),
                    rs.getDouble("unit_price"),
                    Unit.valueOf(rs.getString("unit"))
            );
            ingredients.add(new IngredientQuantity(ingredient, rs.getDouble("required_quantity")));
        }
        return new Dish(
                rs.getInt("id_dish"),
                rs.getString("name"),
                rs.getDouble("unit_price"),
                ingredients
        );
    }
}

    public Dish findById(long id) {

        try {
            return findDishById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish save(Dish entity) {
        String sql = "INSERT INTO dish (name, unit_price) VALUES (?, ?)";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getUnitPrice());

            ResultSet rs  = stmt.executeQuery();
            if (rs.next()) {
                return new Dish(
                        rs.getString("name"),
                        rs.getDouble("unit_price")
                        rs.getInt("id_dish")

                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
