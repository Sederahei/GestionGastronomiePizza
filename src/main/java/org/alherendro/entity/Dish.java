package org.alherendro.entity;
import java.util.List;


public class Dish {
    private int id;
    private String name;
    private double unitPrice;
    private List<IngredientQuantity> ingredients;

    public Dish() {

    }

    public Dish(int id, String name, double unitPrice, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }

    public Dish(int idDish, String name, double unitPrice) {
        this.id = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public double getIngredientCost() {
        return ingredients.stream()
                .mapToDouble(iq -> iq.getIngredient().getUnitPrice() * iq.getRequiredQuantity())
                .sum();
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {    return unitPrice;    }

    public List<IngredientQuantity> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", ingredients=" + ingredients +
                '}';
    }
}