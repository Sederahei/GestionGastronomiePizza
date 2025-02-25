import org.alherendro.dao.IngredientDAO;
import org.alherendro.entity.Ingredient;
import org.junit.jupiter.api.Test;

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
}
