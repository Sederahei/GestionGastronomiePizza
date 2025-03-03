import org.alherendro.DataSource;
import org.alherendro.dao.StockMovementDAO;
import org.alherendro.entity.StockMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class StockMovementDAOTest {

    private StockMovementDAO stockMovementDAO;

    @BeforeEach
    void setUp() throws SQLException {
        stockMovementDAO = new StockMovementDAO();

        try (Connection connection = DataSource.getConnection()) {

            String sql = """
                INSERT INTO stock_movement (id,id_ingredient, movement_type, quantity, unit, movement_datetime)
                VALUES   
                (1, 'ENTREE', 10000, 'G', '2025-02-01 08:00:00'),
                (2, 'ENTREE', 20, 'L', '2025-02-01 08:00:00'),
                (3, 'ENTREE', 100, 'U', '2025-02-01 08:00:00'),
                (4, 'ENTREE', 50, 'U', '2025-02-01 08:00:00');
                """;

            try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
                insertStmt.executeUpdate();
            }
        }
    }

    @Test
    void testGetStockMovements() throws SQLException {
        List<StockMovement> movements = stockMovementDAO.getStockMovements();

        assertNotNull(movements, "La liste des mouvements de stock ne doit pas être null");
        assertEquals(4, movements.size(), "Il doit y avoir exactement 4 mouvements de stock");

        // Vérification d'un mouvement spécifique
        StockMovement firstMovement = movements.get(0);
        assertEquals(1, firstMovement.getId());
        assertEquals(1, firstMovement.getIngredientId());
        assertEquals("ENTREE", firstMovement.getMovementType());
        assertEquals(100, firstMovement.getQuantity());
        assertEquals("U", firstMovement.getUnit());
        assertEquals(LocalDateTime.of(2025, 2, 1, 8, 0), firstMovement.getMovementDate());
    }
}





