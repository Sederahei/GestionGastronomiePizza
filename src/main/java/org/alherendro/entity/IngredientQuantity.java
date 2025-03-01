package org.alherendro.entity;

// record class : classe misy getter ny atribut fa tsy misy setter


public class IngredientQuantity {

    private  Ingredient ingredient;
    private  int id;
    private  double requiredQuantity;


    public IngredientQuantity(Ingredient ingredient, int id, double requiredQuantity) {
        this.ingredient = ingredient;
        this.id = id;
        this.requiredQuantity = requiredQuantity;
    }

    public IngredientQuantity(Ingredient ingredient, double quantity) {
        this.ingredient = ingredient;
        this.requiredQuantity = quantity;
    }
    public IngredientQuantity() {


    }

    public int getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return  ingredient;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    @Override
    public String toString() {
        return "IngredientQuantity{" +
                "ingredient=" + ingredient +
                ", requiredQuantity=" + requiredQuantity +
                '}';
    }
}

