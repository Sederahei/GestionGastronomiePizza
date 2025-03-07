import org.alherendro.dao.DishOrderDAO;
import org.alherendro.dao.OrderDAO;
import org.alherendro.entity.DishOrder;
import org.alherendro.entity.Order;
import org.alherendro.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderDAO orderDAO;
    private DishOrderDAO dishOrderDAO;

    @BeforeEach
    public void setUp() {
        orderDAO = new OrderDAO();
        dishOrderDAO = new DishOrderDAO();
        orderService = new OrderService(orderDAO, dishOrderDAO);
    }

    @Test
    public void testCreateOrder() throws SQLException {

        List<DishOrder> dishOrders = new ArrayList<>();
        DishOrder dishOrder1 = new DishOrder(0, 0, 1, 2); // id 0 car auto increment
        dishOrders.add(dishOrder1);


        Order createdOrder = orderService.createOrder(dishOrders);


        assertNotNull(createdOrder.getId());
        assertEquals(1, dishOrderDAO.findById(1).getDishId());//verifier que le dishorder a bien été créé
        assertEquals(2, dishOrderDAO.findById(1).getQuantity());
        assertEquals(createdOrder.getId(), dishOrderDAO.findById(1).getOrderId());

    }

    @Test
    public void testGetOrderById() throws SQLException {

        List<DishOrder> dishOrders = new ArrayList<>();
        DishOrder dishOrder1 = new DishOrder(0, 0, 1, 2);
        dishOrders.add(dishOrder1);

        Order createdOrder = orderService.createOrder(dishOrders);


        Order retrievedOrder = orderService.getOrderById(createdOrder.getId());


        assertNotNull(retrievedOrder);

        assertEquals(createdOrder.getId(), retrievedOrder.getId());
    }

    // Ajoute des tests pour updateOrder et deleteOrder
}