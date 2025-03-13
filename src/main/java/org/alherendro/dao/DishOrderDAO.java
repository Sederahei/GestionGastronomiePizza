package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.entity.DishOrder;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Dish;

import java.sql.*;

public class DishOrderDAO {

    public DishOrder create(DishOrder dishOrder) throws SQLException {
        String sql = "INSERT INTO dish_order (order_id, dish_id, quantity) VALUES (?, ?, ?) RETURNING id";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, dishOrder.getOrderId());
            statement.setInt(2, dishOrder.getDishId());
            statement.setInt(3, dishOrder.getQuantity());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                dishOrder.setId(generatedKeys.getInt(1));
            }
            // maka an le  dish associated with this order
            DishDAO dishDAO = new DishDAO();
            IngredientDAO ingredientDAO = new IngredientDAO();
            Dish dish = dishDAO.findById(dishOrder.getDishId());
            if (dish != null && dish.getIngredients() != null) {
                for (IngredientQuantity iq : dish.getIngredients()) {
                    ingredientDAO.findById(iq.getIngredient().getId());
                }
            }
        }
        return dishOrder;
    }

    public DishOrder findById(int id) throws SQLException {
        String sql = "SELECT * FROM dish_order WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new DishOrder(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("dish_id"),
                        resultSet.getInt("quantity")
                );
            }
            return null;
        }
    }

    public DishOrder update(DishOrder dishOrder) throws SQLException {
        String sql = "UPDATE dish_order SET order_id = ?, dish_id = ?, quantity = ? WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, dishOrder.getOrderId());
            statement.setInt(2, dishOrder.getDishId());
            statement.setInt(3, dishOrder.getQuantity());
            statement.setInt(4, dishOrder.getId());
            statement.executeUpdate();
        }
        return dishOrder;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM dish_order WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }



}