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

    public Dish(int id, String name, List<IngredientQuantity> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    public Dish(int idIngredient, int idDish, String name, double unitPrice) {
        this.id = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
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

    public Dish(int idDish, String name, double unitPrice) {
        this.id = idDish;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Dish(long dishId, String dishName, double dishPrice, List<IngredientQuantity> ingredients) {
        this.id = (int) dishId;
        this.name = dishName;
        this.unitPrice = dishPrice;
        this.ingredients = ingredients;
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


    // Quesion N°4

    public double getIngredientCost() {
        double sum = ingredients.stream()
                .mapToDouble(iq -> iq.getIngredient().getUnitPrice() * iq.getRequiredQuantity())
                .sum();
        return sum;
    }


    public double getCost() {
        return getIngredientCost();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setId(int id) {
        this.id = id;
    }


    public Dish get() {
        return this;
    }
}