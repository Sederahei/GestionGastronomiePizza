import org.alherendro.dao.IngredientDAO;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {
    @Test
    void get_all_ingredients_ok() {

        // ==> Preparation
        IngredientDAO ingredientDAO = new IngredientDAO();

        // ==> Execution
        List<Ingredient> ingredients = (List<Ingredient>) ingredientDAO.getAll();

        // ==> Verification
        System.out.println(ingredients);
        assertTrue(ingredients.size()>0);
    }

    @Test
    void hot_dog_const_ingredient_55000() throws Exception {

        // Données de test
        Ingredient sausage = new Ingredient(1, "Sausage", LocalDateTime.of(2025, 1, 1, 0, 0), 20.0, Unit.G);
        Ingredient oil = new Ingredient(2, "Oil", LocalDateTime.of(2025, 1, 1, 0, 0), 10000.0, Unit.L);
        List<IngredientQuantity> components = DishDAOTest.getIngredientQuantities(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), sausage, oil);

        // Création du plat Hot Dog
        Dish hotDog = new Dish(1, "Hot Dog", 15000.0, components);

        // Vérification du coû total attendu (5500 Ar)
        System.out.println(hotDog.getIngredientCost());
        assertEquals(5500.0, hotDog.getIngredientCost(), 0.001, "Le coû total des ingrédients du Hot Dog doit être 5500 Ar.");
    }
}
