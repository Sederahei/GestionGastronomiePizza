package org.alherendro.entity;

// record class : classe misy getter ny atribut fa tsy misy setter


public class IngredientQuantity {

    private final Ingredient ingredient;
    private final double requiredQuantity;

    public IngredientQuantity(Ingredient ingredient, double requiredQuantity) {
        this.ingredient = ingredient;
        this.requiredQuantity = requiredQuantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
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

