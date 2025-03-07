package org.alherendro.service;

import org.alherendro.dao.DishOrderDAO;
import org.alherendro.entity.DishOrder;

import java.sql.SQLException;

public class DishOrderService {

    private DishOrderDAO dishOrderDAO;

    public DishOrderService(DishOrderDAO dishOrderDAO) {
        this.dishOrderDAO = dishOrderDAO;
    }

    public DishOrder createDishOrder(DishOrder dishOrder) throws SQLException {
        return dishOrderDAO.create(dishOrder);
    }

    public DishOrder getDishOrderById(int id) throws SQLException {
        return dishOrderDAO.findById(id);
    }

    public DishOrder updateDishOrder(DishOrder dishOrder) throws SQLException {
        return dishOrderDAO.update(dishOrder);
    }

    public void deleteDishOrder(int id) throws SQLException {
        dishOrderDAO.delete(id);
    }
}