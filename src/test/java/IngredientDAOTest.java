import org.alherendro.dao.IngredientDAO;
import org.alherendro.entity.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {
    @Test
    void get_all_ingredients_ok() {

        IngredientDAO ingredientDAO = new IngredientDAO();
        List<Ingredient> ingredients = ingredientDAO.getAll();
        System.out.println(ingredients);
        assertTrue(ingredients.size()>0);
    }
}
