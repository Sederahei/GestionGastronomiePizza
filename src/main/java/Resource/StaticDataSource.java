package Resource;


import org.alherendro.Etinty.Dish;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.IngredientQuantity;
import org.alherendro.Etinty.Unit;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StaticDataSource {
        public static Dish getHotDogDish() {
            List<IngredientQuantity> ingredients = Arrays.asList(
                    new IngredientQuantity(new Ingredient(1, "Saucisse", LocalDateTime.now(), 20, Unit.G), 100),
                    new IngredientQuantity(new Ingredient(2, "Huile", LocalDateTime.now(), 10000, Unit.L), 0.15),
                    new IngredientQuantity(new Ingredient(3, "Oeuf", LocalDateTime.now(), 1000, Unit.U), 1),
                    new IngredientQuantity(new Ingredient(4, "Pain", LocalDateTime.now(), 1000, Unit.U), 1)
            );
            return new Dish(1, "Hot Dog", 15000, ingredients);
        }
    }
