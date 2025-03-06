package org.alherendro.entity;

import java.time.LocalDateTime;

public class StockHistory {
    private int id;
    private int ingredientId;
    private LocalDateTime stockDateTime;
    private double quantity;
    private Unit unit;

    public StockHistory(int id, int ingredientId, LocalDateTime stockDateTime, double quantity, Unit unit) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.stockDateTime = stockDateTime;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public LocalDateTime getStockDateTime() {
        return stockDateTime;
    }

    public void setStockDateTime(LocalDateTime stockDateTime) {
        this.stockDateTime = stockDateTime;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "StockHistory{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", stockDateTime=" + stockDateTime +
                ", quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}