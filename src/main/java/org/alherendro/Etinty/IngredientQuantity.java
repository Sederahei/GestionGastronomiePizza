package org.alherendro.Etinty;


    public class IngredientQuantity {

        private Ingredient ingredient;
        private double requiredQuantity;

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

