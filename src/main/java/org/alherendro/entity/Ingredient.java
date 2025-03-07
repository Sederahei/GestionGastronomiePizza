package org.alherendro.entity;
import org.alherendro.dao.StockMovementDAO;

import java.time.LocalDateTime;
import java.util.List;

public class Ingredient {
    private int id;
    private String name;
    private LocalDateTime updateDatetime;
    private double unitPrice;
    private Unit unit;

    public Ingredient(int id, String name, double unitPrice, LocalDateTime updateDatetime, Unit unit) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.updateDatetime = updateDatetime;
        this.unit = unit;
    }

    public Ingredient(int ingredientId, String ingredientName, double unitPrice) {
        this.id = ingredientId;
        this.name = ingredientName;
        this.unitPrice = unitPrice;
    }

    public Ingredient(int idIngredient, String ingredientName, double unitPrice, Unit unit) {
        this.id = idIngredient;
        this.name = ingredientName;
        this.unitPrice = unitPrice;
        this.unit = unit;
    }

    public Ingredient(int id, String name, LocalDateTime updateDatetime, double unitPrice, Unit unit) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
    }

    public Ingredient() {
    }

    public static void add(Ingredient ingredient) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public LocalDateTime getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(LocalDateTime updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getIngredientCost() {
        return unitPrice;
    }

    public List<Ingredient> findFilteredAndPaginated(Object o, Object o1, Object o2, Object o3, Object o4, Object o5, String id, boolean b, int i, int i1) {
        return List.of();
    }

    public Ingredient getIngredientById(int i) {
        return null;
    }

    public double getAvailableQuantity(LocalDateTime date) {
        StockMovementDAO stockMovementDAO = new StockMovementDAO(null); // A ameliorer
        return stockMovementDAO.getAvailableStock(this.getId(), date);
    }

    public double getAvailableQuantity() {
        return getAvailableQuantity(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", unitPrice=" + unitPrice +
                ", unit=" + unit +
                '}';
    }
}