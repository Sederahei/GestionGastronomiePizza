import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import org.alherendro.DataSource;
import org.alherendro.dao.StockMovementDAO;
import org.alherendro.entity.MovementType;
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
        StockMovement movement = new StockMovement(0, 1, MovementType.IN, 500, Unit.G, LocalDateTime.now());  // Utilisation de MovementType.IN
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


    @BeforeAll
    static void setup() {
    }

    @AfterEach
    void cleanup() {
        clearStockMovements();
    }

    @Test
    @Order(1)
    void testStockBeforeAnyMovement() {
        double stock = stockMovementDAO.getAvailableStock(1, LocalDateTime.now());  // ✅ Utilisation correcte de stockMovementDAO
        assertEquals(0, stock, "Le stock initial doit être 0.");
    }


    @Test
    @Order(5)
    void testStockForIngredientWithoutMovements() {
        double stock = stockMovementDAO.getAvailableStock(99, LocalDateTime.now());
        assertEquals(0, stock, "Un ingrédient sans mouvement doit avoir un stock de 0.");
    }

    private void insertStockMovement(int ingredientId, double quantity, Unit unit, LocalDateTime date, MovementType type) {
        String sql = "INSERT INTO stock_movement (id_ingredient, quantity, unit, movement_datetime, movement_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingredientId);
            stmt.setDouble(2, quantity);
            stmt.setString(3, unit.toString());
            stmt.setTimestamp(4, Timestamp.valueOf(date));
            stmt.setString(5, type.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de stock", e);
        }
    }

    private void clearStockMovements() {
        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM stock_movement")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du nettoyage des mouvements de stock", e);
        }
    }

    @Test
    @Order(2)
    void testStockAfterEntry() {
        insertStockMovement(1, 100, Unit.G, LocalDateTime.of(2025, 2, 1, 10, 0, 0), MovementType.IN);
        double stock = stockMovementDAO.getAvailableStock(1, LocalDateTime.now());
        assertEquals(100, stock, "Le stock après une entrée doit être 100.");
    }

    @Test
    @Order(3)
    void testStockAfterEntryAndExit() {
        insertStockMovement(1, 100, Unit.G, LocalDateTime.of(2025, 2, 1, 10, 0, 0), MovementType.IN);
        insertStockMovement(1, 30, Unit.G, LocalDateTime.of(2025, 2, 5, 15, 30, 0), MovementType.OUT);
        double stock = stockMovementDAO.getAvailableStock(1, LocalDateTime.now());
        assertEquals(70, stock, "Le stock après une entrée de 100 et une sortie de 30 doit être 70.");
        System.out.println("stock = " + stock);
    }

    @Test
    @Order(4)
    void testStockOnFutureDate() {
        insertStockMovement(1, 50, Unit.G, LocalDateTime.of(2025, 2, 2, 12, 0, 0), MovementType.IN);
        double stock = stockMovementDAO.getAvailableStock(1, LocalDateTime.now());
        assertEquals(70, stock, "Le stock ne doit pas inclure les entrées futures.");
    }

    // Ajout d'un test pour recordStockHistory
    @Test
    void testRecordStockHistory() throws SQLException {
        int ingredientId = 1;
        LocalDateTime stockDateTime = LocalDateTime.now();
        double quantity = 100.0;
        Unit unit = Unit.G;
        System.out.println("ingredientId = " + ingredientId);
        System.out.println("stockDateTime = " + stockDateTime);
        System.out.println("quantity = " + quantity);
        System.out.println("unit = " + unit);
        stockMovementDAO.recordStockHistory(ingredientId, stockDateTime, quantity, unit);


    }
}
