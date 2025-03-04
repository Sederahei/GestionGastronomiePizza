package org.alherendro.Interface;
import org.alherendro.entity.StockMovement;
import java.sql.SQLException;
import java.util.List;




public interface StockMovementRepository {
    void addStockMovement(StockMovement stockMovement) throws SQLException;
    StockMovement getStockMovementById(int id) throws SQLException;
    List<StockMovement> getAllStockMovements() throws SQLException;
    void updateStockMovement(StockMovement stockMovement) throws SQLException;
    void deleteStockMovement(int id) throws SQLException;
}
