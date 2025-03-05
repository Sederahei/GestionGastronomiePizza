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


    public StockMovementDAO(Connection connection) {

    }

    @Override
    public StockMovement getStockMovementById(int id) throws SQLException {
        String sql = "SELECT * FROM stock_movement WHERE id = ?";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movements.add(mapResultSetToStockMovement(rs));
            }
        }
        return movements;
    }

    @Override
    public void updateStockMovement(StockMovement stockMovement) throws SQLException {
        String sql = "UPDATE stock_movement SET id_ingredient = ?, movement_type = ?, quantity = ?, unit = ?, movement_datetime = ? WHERE id = ?";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, stockMovement.getIngredientId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){

            }

        }
    }

    @Override
    public void deleteStockMovement(int id) throws SQLException {
        String sql = "DELETE FROM stock_movement WHERE id = ?";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
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


    public double getAvailableQuantity(int ingredientId, LocalDateTime date) {
        String query = "SELECT " +
                "COALESCE(SUM(CASE WHEN movement_type = 'ENTREE' THEN quantity ELSE 0 END), 0) - " +
                "COALESCE(SUM(CASE WHEN movement_type = 'SORTIE' THEN quantity ELSE 0 END), 0) " +
                "FROM stock_movement WHERE id_ingredient = ? AND movement_datetime <= ?";

        try (
                Connection connection=  DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ingredientId);
            stmt.setTimestamp(2, Timestamp.valueOf(date));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du stock disponible", e);
        }
    }

    public int addStockMovement(StockMovement stockMovement) {

        /* String query = "INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime) \" +\n" +
                "                   \"VALUES (?, ?, ?, ?::unit, ?) RETURNING id";*/
        String query = "INSERT INTO stock_movement (id_ingredient, movement_type, quantity, unit, movement_datetime) \n" +
                "                   VALUES (?, ?, ?, ?::unit, ?) RETURNING id";

        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, stockMovement.getIngredientId());
            stmt.setString(2, stockMovement.getMovementType());
            stmt.setDouble(3, stockMovement.getQuantity());
            stmt.setString(4, stockMovement.getUnit().name());
            stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1; // Erreur
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du mouvement de stock", e);
        }
    }


}



