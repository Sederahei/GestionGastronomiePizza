package org.alherendro.Etinty;
import java.util.List;

public class Dish {


    private int id;
    private String name;
    private double unitPrice;
    private List<IngredientQuantity> ingredients;

    public Dish(int id, String name, double unitPrice, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.ingredients = ingredients;
    }

    public double getIngredientCost() {
        return ingredients.stream()
                .mapToDouble(iq -> iq.getIngredient().getUnitPrice() * iq.getRequiredQuantity())
                .sum();
    }

    public String getName() {
        return name;
    }
}