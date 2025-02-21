package org.alherendro.Etinty;

import org.alherendro.Etinty.Unit;

import java.time.LocalDateTime;

public class Ingredient {
    private int id;
    private String name;
    private LocalDateTime updateDatetime;
    private double price;
    private Unit unit;

    public Ingredient(int id, String name, LocalDateTime updateDatetime, double price, Unit unit) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.price = price;
        this.unit = unit;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}