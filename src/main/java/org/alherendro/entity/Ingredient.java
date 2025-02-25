package org.alherendro.entity;

import java.time.LocalDateTime;

public class Ingredient {
    //getter sy setter mila tao (jereo ny teneniny surlignignage jaune reny)
    private int id;
    private String name;
    private LocalDateTime updateDatetime;
    private double unitPrice;
    private Unit unit;

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

    public Ingredient(int id, String name, LocalDateTime updateDatetime, double unitPrice, Unit unit) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
    }
    public Ingredient() {

    }

    public double getUnitPrice() {
        return unitPrice;
    }
}