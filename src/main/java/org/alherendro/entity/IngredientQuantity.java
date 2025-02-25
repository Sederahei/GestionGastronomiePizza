package org.alherendro.entity;


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
    }

