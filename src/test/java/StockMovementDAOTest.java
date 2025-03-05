import java.time.temporal.ChronoUnit;
import org.alherendro.DataSource;
import org.alherendro.dao.StockMovementDAO;
import org.alherendro.entity.StockMovement;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StockMovementDAOTest {

    private Connection connection;
    private StockMovementDAO stockMovementDAO;

    @BeforeAll
    void setupDatabase() throws SQLException {

        connection = DataSource.getConnection();
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Failed to establish database connection.");
        }


        stockMovementDAO = new StockMovementDAO(connection);
    }



    @Test
    void testUpdateStockMovement() throws SQLException {
        StockMovement movement = new StockMovement(2, 1, "ENTREE", 0, Unit.G, LocalDateTime.now());
        stockMovementDAO.addStockMovement(movement);

        System.out.println("ID après insertion : " + movement.getId());

        movement.setQuantity(600);
        stockMovementDAO.updateStockMovement(movement);

        StockMovement updated = stockMovementDAO.getStockMovementById(movement.getId());
        assertEquals(600, updated.getQuantity());
    }


    @Test
    void testDeleteStockMovement() throws SQLException {
        StockMovement movement = new StockMovement(0, 1, "SORTIE", 200, Unit.G, LocalDateTime.now());
        stockMovementDAO.addStockMovement(movement);

        stockMovementDAO.deleteStockMovement(movement.getId());
        StockMovement deleted = stockMovementDAO.getStockMovementById(movement.getId());
        assertNull(deleted);
    }

    @Test
    void testGetAllStockMovements() throws SQLException {
        List<StockMovement> movements = stockMovementDAO.getAllStockMovements();
        assertNotNull(movements);
        assertTrue(movements.size() >= 0);


    }



    @Test
    void testAddAndGetStockMovement() throws SQLException {
        StockMovement movement = new StockMovement(0, 1, "ENTREE", 500, Unit.G, LocalDateTime.now());
        int generatedId = stockMovementDAO.addStockMovement(movement);

        StockMovement retrieved = stockMovementDAO.getStockMovementById(generatedId);
        assertNotNull(retrieved);
        assertEquals(movement.getIngredientId(), retrieved.getIngredientId());
        assertEquals(movement.getMovementType(), retrieved.getMovementType());
        assertEquals(movement.getQuantity(), retrieved.getQuantity());
        assertEquals(movement.getUnit(), retrieved.getUnit());
        assertEquals(movement.getMovementDate().truncatedTo(ChronoUnit.MILLIS), retrieved.getMovementDate());
       // assertEquals(expected.truncatedTo(ChronoUnit.MICROS), actual.truncatedTo(ChronoUnit.MICROS));

    }

    @Test
    void testGetAvailableQuantity() {
        int ingredientId = 1;
        LocalDateTime testDate = LocalDateTime.of(2025, 2, 24, 0, 0);

        double expectedQuantity = 10000.00;
        double availableQuantity = stockMovementDAO.getAvailableQuantity(ingredientId, testDate);

        System.out.println("availableQuantity = " + availableQuantity);
        assertEquals(expectedQuantity, availableQuantity, 0.01);
    }




}
