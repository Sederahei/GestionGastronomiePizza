package org.alherendro.Etinty;
import java.util.List;

public class Dish {
    private int id;
    private String name;
    private double price;
    private List<IngredientQuantity> ingredients;

    public Dish(int id, String name, double price, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public double getIngredientCost() {
        return ingredients.stream()
                .mapToDouble(iq -> iq.getIngredient().getPrice() * iq.getQuantity())
                .sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IngredientQuantity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientQuantity> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}