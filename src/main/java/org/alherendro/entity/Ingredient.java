package org.alherendro.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Ingredient {
    //getter sy setter mila tao (jereo ny teneniny surlignignage jaune reny)
    private int id;
    private String name;
    private LocalDateTime updateDatetime;
    private double unitPrice;
    private Unit unit;


    public Ingredient(int anInt, String name, double unitPrice, LocalDateTime updateDateTime, int unit) {

        this.id = anInt;
        this.name = name;
        this.unitPrice = unitPrice;
        this.updateDatetime = updateDateTime;
        this.unit = Unit.values()[unit];
    }

    public Ingredient(int ingredientId, String ingredientName, double unitPrice) {

        this.id = ingredientId;
        this.name = ingredientName;
        this.unitPrice = unitPrice;


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

    public double getIngredientCost() {

        return unitPrice;
    }


    public List<Ingredient> findFilteredAndPaginated(Object o, Object o1, Object o2, Object o3, Object o4, Object o5, String id, boolean b, int i, int i1) {
        return List.of();
    }
}