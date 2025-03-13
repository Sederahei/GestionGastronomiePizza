package org.alherendro.dao;
import org.alherendro.DataSource;
import org.alherendro.entity.Order;
import java.sql.*;
public class OrderDAO {

    public Order create(Order order) throws SQLException {
        String sql = "INSERT INTO \"order\" (order_date) VALUES (?) RETURNING id";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getInt(1));
            }
        }
        return order;
    }

    public Order findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"order\" WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Order(
                        resultSet.getInt("id"),
                        resultSet.getTimestamp("order_date").toLocalDateTime()
                );
            }
            return null;
        }
    }

    public Order update(Order order) throws SQLException {
        String sql = "UPDATE \"order\" SET order_date = ? WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            statement.setInt(2, order.getId());
            statement.executeUpdate();
        }
        return order;
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM \"order\" WHERE id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    
}