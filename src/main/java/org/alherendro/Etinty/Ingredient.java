package org.alherendro.Etinty;

import org.alherendro.Etinty.Unit;

import java.time.LocalDateTime;

public class Ingredient {
    private int id;
    private String name;
    private LocalDateTime updateDatetime;
    private double unitPrice;
    private Unit unit;

    public Ingredient(int id, String name, LocalDateTime updateDatetime, double unitPrice, Unit unit) {
        this.id = id;
        this.name = name;
        this.updateDatetime = updateDatetime;
        this.unitPrice = unitPrice;
        this.unit = unit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}