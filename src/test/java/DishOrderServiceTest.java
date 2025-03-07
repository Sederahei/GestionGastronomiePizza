import org.alherendro.dao.DishOrderDAO;
import org.alherendro.entity.DishOrder;
import org.alherendro.service.DishOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DishOrderServiceTest {

    private DishOrderService dishOrderService;
    private DishOrderDAO dishOrderDAO;

    @BeforeEach
    public void setUp() {
        dishOrderDAO = new DishOrderDAO();
        dishOrderService = new DishOrderService(dishOrderDAO);
    }

    @Test
    public void testCreateDishOrder() throws SQLException {

        DishOrder dishOrder = new DishOrder(0, 1, 1, 2);

        DishOrder createdDishOrder = dishOrderService.createDishOrder(dishOrder);


        assertNotNull(createdDishOrder.getId());
        assertEquals(1, createdDishOrder.getOrderId());
        assertEquals(1, createdDishOrder.getDishId());
        assertEquals(2, createdDishOrder.getQuantity());
    }

    @Test
    public void testGetDishOrderById() throws SQLException {

        DishOrder dishOrder = new DishOrder(0, 1, 1, 2);
        DishOrder createdDishOrder = dishOrderService.createDishOrder(dishOrder);


        DishOrder retrievedDishOrder = dishOrderService.getDishOrderById(createdDishOrder.getId());


        assertNotNull(retrievedDishOrder);
        assertEquals(createdDishOrder.getId(), retrievedDishOrder.getId());
    }

    // Ajoute des tests pour updateDishOrder et deleteDishOrder
}