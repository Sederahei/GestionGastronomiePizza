import org.alherendro.DataSource;
import org.alherendro.dao.IngredientDAO;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {

    @Test
    void save_ingredient() {
        // GIVEN : Nouvel ingrédient "Saucisse"
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Saucisse");
        ingredient.setUpdateDatetime(LocalDateTime.of(2025, 1, 1, 0, 0));
        ingredient.setUnitPrice(20);
        ingredient.setUnit(Unit.G); // Enum

        // WHEN : on insère en base
        IngredientDAO ingredientDAO = new IngredientDAO();
        Ingredient savedIngredient = ingredientDAO.save(ingredient);

        // THEN : Vérifier qu'il a bien été sauvegardé
        assertNotNull(savedIngredient.getId(), "L'ID ne doit pas être nul après insertion");
        assertEquals("Saucisse", savedIngredient.getName(), "Le nom de l'ingrédient doit être correct");
        assertEquals(20, savedIngredient.getUnitPrice(), 0.01, "Le prix unitaire doit être correct");
        assertEquals(Unit.G, savedIngredient.getUnit(), "L'unité doit être correcte");
    }

    @Test
    void save_multiple_ingredients() {
        // GIVEN : Liste d'ingrédients
        Ingredient[] ingredients = {
                new Ingredient(1, "Saucisse", LocalDateTime.of(2025, 1, 1, 0, 0), 20, Unit.G),
                new Ingredient(2, "Huile", LocalDateTime.of(2025, 1, 1, 0, 0), 10000, Unit.L),
                new Ingredient(3, "Oeuf", LocalDateTime.of(2025, 1, 1, 0, 0), 1000, Unit.U),
                new Ingredient(4, "Pain", LocalDateTime.of(2025, 1, 1, 0, 0), 1000, Unit.U)
        };

        for (Ingredient ingredient : ingredients) {
            IngredientDAO ingredientDAO = new IngredientDAO();
            Ingredient saved = ingredientDAO.save(ingredient);
            assertNotNull(saved.getId(), "L'ID ne doit pas être nul");
            assertEquals(ingredient.getName(), saved.getName(), "Le nom doit correspondre");
            assertEquals(ingredient.getUnitPrice(), saved.getUnitPrice(), 0.01, "Le prix doit correspondre");
            assertEquals(ingredient.getUnit(), saved.getUnit(), "L'unité doit correspondre");
        }
    }

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

    }



