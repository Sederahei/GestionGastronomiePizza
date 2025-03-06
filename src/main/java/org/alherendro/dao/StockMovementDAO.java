package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.Interface.StockMovementRepository;
import org.alherendro.entity.MovementType;
import org.alherendro.entity.StockMovement;
import org.alherendro.entity.Unit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDAO implements StockMovementRepository {


    public StockMovementDAO(Connection connection) {

    }

    @Override
    public StockMovement getStockMovementById(int id) throws SQLException {
        String sql = "SELECT * FROM stock_movement WHERE id = ?";
        try (
                Connection connection = DataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStockMovement(rs);
                } else {
                    System.out.println("Aucun mouvement trouvé avec l'ID : " + id);
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
            try {
                stmt.setInt(1, stockMovement.getIngredientId());
                stmt.setString(2, stockMovement.getMovementType().name());
                stmt.setDouble(3, stockMovement.getQuantity());
                stmt.setObject(4, stockMovement.getUnit().name(), java.sql.Types.OTHER);
                stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate()));
                stmt.setInt(6, stockMovement.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
            MovementType movementType = MovementType.valueOf(rs.getString("movement_type"));  // Conversion String vers MovementType
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
            stmt.setString(2, stockMovement.getMovementType().name());
            stmt.setDouble(3, stockMovement.getQuantity());
            stmt.setString(4, stockMovement.getUnit().name());
            stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate().truncatedTo(ChronoUnit.MILLIS)));
           // stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getMovementDate().truncatedTo(ChronoUnit.MICROS)));


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1; // Erreur
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du mouvement de stock", e);
        }
    }



    public double getAvailableStock(int ingredientId, LocalDateTime localDateTime) {
        if (ingredientId <= 0) {
            throw new IllegalArgumentException("ingredientId must be positive");
        }

        String sql = """
        SELECT 
            COALESCE(SUM(CASE WHEN movement_type = 'IN' THEN quantity ELSE 0 END), 0) - 
            COALESCE(SUM(CASE WHEN movement_type = 'OUT' THEN quantity ELSE 0 END), 0) 
        FROM stock_movement 
        WHERE id_ingredient = ? AND movement_datetime <= ?;
    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingredientId);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            stmt.setTimestamp(2, timestamp);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du stock disponible", e);
        }
        return 0;
    }

    public void recordStockHistory(int ingredientId, LocalDateTime stockDateTime, double quantity, Unit unit) throws SQLException {
        if (ingredientId <= 0) {
            throw new IllegalArgumentException("ingredientId must be positive");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must not be negative");
        }

        String sql = "INSERT INTO stock_history (id_ingredient, stock_datetime, quantity, unit) VALUES (?, ?, ?, ?::unit)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingredientId);
            stmt.setTimestamp(2, Timestamp.valueOf(stockDateTime));
            stmt.setDouble(3, quantity);
            stmt.setString(4, unit.name()); // Utilisation de unit.name()
            stmt.executeUpdate();
        }
    }

}



