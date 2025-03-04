package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.Interface.StockMovementRepository;
import org.alherendro.entity.StockMovement;
import org.alherendro.entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class StockMovementDAO implements StockMovementRepository {
    private final Connection connection = DataSource.getConnection();

    public StockMovementDAO(Connection connection) throws SQLException {
    }

    @Override
    public void addStockMovement(StockMovement stockMovement) throws SQLException {
        String sql = "INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, stockMovement.getIngredientId());
            stmt.setString(2, stockMovement.getMovementType());
            stmt.setDouble(3, stockMovement.getQuantity());
            stmt.setString(4, stockMovement.getUnit().name());
            stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    stockMovement.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public StockMovement getStockMovementById(int id) throws SQLException {
        String sql = "SELECT * FROM stock_movement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStockMovement(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<StockMovement> getAllStockMovements() throws SQLException {
        List<StockMovement> movements = new ArrayList<>();
        String sql = "SELECT * FROM stock_movement";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movements.add(mapResultSetToStockMovement(rs));
            }
        }
        return movements;
    }

    @Override
    public void updateStockMovement(StockMovement stockMovement) throws SQLException {
        String sql = "UPDATE stock_movement SET id_ingredient = ?, movement_type = ?, quantity = ?, unit = ?, movement_datetime = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, stockMovement.getIngredientId());
            stmt.setString(2, stockMovement.getMovementType());
            stmt.setDouble(3, stockMovement.getQuantity());
            stmt.setString(4, stockMovement.getUnit().name());
            stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));
            stmt.setInt(6, stockMovement.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteStockMovement(int id) throws SQLException {
        String sql = "DELETE FROM stock_movement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private StockMovement mapResultSetToStockMovement(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int ingredientId = rs.getInt("id_ingredient");
        String movementType = rs.getString("movement_type");
        double quantity = rs.getDouble("quantity");
        Unit unit = Unit.valueOf(rs.getString("unit"));
        LocalDateTime movementDate = rs.getTimestamp("movement_datetime").toLocalDateTime();
        return new StockMovement(id, ingredientId, movementType, quantity, unit, movementDate);
    }
}
