package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.entity.StockMovement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO {


    public List<StockMovement> getStockMovements() throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        String sql = "SELECT id, ingredient_id, movement_type, quantity, unit, movement_date FROM stock_movement";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                StockMovement movement = new StockMovement(
                        rs.getInt("id"),
                        rs.getInt("ingredient_id"),
                        rs.getString("movement_type"),
                        rs.getDouble("quantity"),
                        rs.getString("unit"),
                        rs.getTimestamp("movement_date").toLocalDateTime()
                );
                movements.add(movement);
            }
        }catch (SQLException e) {
            new RuntimeException(e);
        }
        return movements;
    }
}

