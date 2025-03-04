import org.alherendro.DataSource;
import org.alherendro.dao.StockMovementDAO;
import org.alherendro.entity.StockMovement;
import org.alherendro.entity.Unit; // Ajouter l'import de l'Enum Unit
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
        // Utiliser DataSource pour obtenir la connexion
        connection = DataSource.getConnection();
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Failed to establish database connection.");
        }
        stockMovementDAO = new StockMovementDAO(connection); // Passer la connexion au DAO
    }

    @AfterAll
    void closeDatabase() throws SQLException {
        // Fermer la connexion après les tests
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void testAddAndGetStockMovement() throws SQLException {
        StockMovement movement = new StockMovement(0, 1, "ENTREE", 500, Unit.G, LocalDateTime.now());
        stockMovementDAO.addStockMovement(movement);

        StockMovement retrieved = stockMovementDAO.getStockMovementById(movement.getId());
        assertNotNull(retrieved);
        assertEquals(movement.getIngredientId(), retrieved.getIngredientId());
        assertEquals(movement.getMovementType(), retrieved.getMovementType());
        assertEquals(movement.getQuantity(), retrieved.getQuantity());
        assertEquals(movement.getUnit(), retrieved.getUnit());
        assertEquals(movement.getMovementDate(), retrieved.getMovementDate());

    }

    @Test
    void testUpdateStockMovement() throws SQLException {
        StockMovement movement = new StockMovement(0, 1, "ENTREE", 500, Unit.G, LocalDateTime.now());
        stockMovementDAO.addStockMovement(movement);

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
}
