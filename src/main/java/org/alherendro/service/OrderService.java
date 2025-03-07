package org.alherendro.service;

import org.alherendro.dao.DishOrderDAO;
import org.alherendro.dao.OrderDAO;
import org.alherendro.entity.DishOrder;
import org.alherendro.entity.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;
    private DishOrderDAO dishOrderDAO;

    public OrderService(OrderDAO orderDAO, DishOrderDAO dishOrderDAO) {
        this.orderDAO = orderDAO;
        this.dishOrderDAO = dishOrderDAO;
    }

    public Order createOrder(List<DishOrder> dishOrders) throws SQLException {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order = orderDAO.create(order);

        for (DishOrder dishOrder : dishOrders) {
            dishOrder.setOrderId(order.getId());
            dishOrderDAO.create(dishOrder);
        }

        return order;
    }

    public Order getOrderById(int id) throws SQLException {
        return orderDAO.findById(id);
    }

    public Order updateOrder(Order order) throws SQLException {
        return orderDAO.update(order);
    }

    public void deleteOrder(int id) throws SQLException {
        orderDAO.delete(id);
    }
}