package org.alherendro.entity;

import java.util.List;

public class DishOrder {
    private int id;
    private int orderId;
    private int dishId;
    private int quantity;
    private List<DishOrderStatus> dishOrderStatuses;

    public DishOrder() {
    }

    public DishOrder(int id, int orderId, int dishId, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<DishOrderStatus> getDishOrderStatuses() {
        return dishOrderStatuses;
    }

    public void setDishOrderStatuses(List<DishOrderStatus> dishOrderStatuses) {
        this.dishOrderStatuses = dishOrderStatuses;
    }
}