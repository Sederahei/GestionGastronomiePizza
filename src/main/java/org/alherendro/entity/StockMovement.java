package org.alherendro.entity;

import java.time.LocalDateTime;

public class StockMovement {
    private int id;
    private int ingredientId;
    private String movementType; // "ENTREE" ou "SORTIE"
    private double quantity;
    private String unit; // "G", "U", "L"
    private LocalDateTime movementDate;

    public StockMovement(int id, int ingredientId, String movementType, double quantity, String unit, LocalDateTime movementDate) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDate = movementDate;
    }

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

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public StockMovement(int id, LocalDateTime movementDate, double quantity, String movementType, int ingredientId, String unit) {
        this.id = id;
        this.movementDate = movementDate;
        this.quantity = quantity;
        this.movementType = movementType;
        this.ingredientId = ingredientId;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", movementType='" + movementType + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", movementDate=" + movementDate +
                '}';
    }
}

