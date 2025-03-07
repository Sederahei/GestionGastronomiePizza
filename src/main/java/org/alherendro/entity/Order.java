package org.alherendro.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime orderDate;
    private List<DishOrder> dishOrders;
    private List<OrderStatus> orderStatuses;

    public Order() {
    }

    public Order(int id, LocalDateTime orderDate) {
        this.id = id;
        this.orderDate = orderDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<DishOrder> getDishOrders() {
        return dishOrders;
    }

    public void setDishOrders(List<DishOrder> dishOrders) {
        this.dishOrders = dishOrders;
    }

    public List<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }

    public void setOrderStatuses(List<OrderStatus> orderStatuses) {
        this.orderStatuses = orderStatuses;
    }
}