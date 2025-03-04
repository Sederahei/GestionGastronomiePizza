import org.alherendro.DataSource;
import org.alherendro.dao.IngredientDAO;
import org.alherendro.dao.StockMovementDAO;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.StockMovement;
import org.alherendro.entity.Unit; // Ajouter l'import de l'Enum Unit
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
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



    @Test
    public void testGetAvailableQuantity() {

        int ingredientId = 1;
        LocalDateTime testDate = LocalDateTime.of(2025, 2, 24, 0, 0, 0, 0);


        double expectedQuantity = 10000.00;

        double availableQuantity = stockMovementDAO.getAvailableQuantity(ingredientId, testDate);
        System.out.println( "availableQuantity = " + availableQuantity);
        assertEquals(expectedQuantity, availableQuantity, 0.01);
    }

    @Test
    public void testAddStockMovement() {

        StockMovement stockMovement = new StockMovement(0, 1, "ENTREE", 5000.00, Unit.G, LocalDateTime.of(2025, 3, 1, 10, 0, 0, 0));


        stockMovementDAO.addStockMovement(stockMovement);
        double availableQuantity = getAvailableQuantity(1, LocalDateTime.of(2025, 3, 1, 10, 0, 0, 0));
        assertEquals(5000.00, availableQuantity, 0.01); // 10 000 g + 5000 g => 15 000 g
    }

    private double getAvailableQuantity(int i, LocalDateTime dateTime) {
        return 0;
    }



}
