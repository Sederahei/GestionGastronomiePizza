package org.alherendro.entity;
import java.time.LocalDateTime;


public class StockMovement {
    private int id;
    private int ingredientId;
    private MovementType movementType; // Utilisation de l'énumération MovementType
    private double quantity;
    private Unit unit;
    private LocalDateTime movementDate;

    public StockMovement(int id, int ingredientId, MovementType movementType, double quantity, Unit unit, LocalDateTime movementDate) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.movementType = movementType;
        this.quantity = quantity;
        this.unit = unit;
        this.movementDate = movementDate;
    }

    public StockMovement(int id, int ingredientId, String movementType, double quantity, Unit unit, LocalDateTime movementDate) {
        this(id, ingredientId, MovementType.valueOf(movementType), quantity, unit, movementDate);  // Conversion du String en MovementType
    }

    public StockMovement() {
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

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
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

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", ingredientId=" + ingredientId +
                ", movementType=" + movementType +  // Affichage avec l'énumération
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", movementDate=" + movementDate +
                '}';
    }


    }




