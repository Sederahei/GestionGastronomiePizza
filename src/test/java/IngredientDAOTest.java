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

    @Test
    void hot_dog_const_ingredient_55000() throws Exception {
    }


    @Test
    void getIngredientCost() {
        IngredientDAO ingredientDAO = new IngredientDAO();
        List<Ingredient> ingredients = ingredientDAO.getAll();
        IngredientQuantity iq1 = new IngredientQuantity(ingredients.get(0), 1);
        IngredientQuantity iq2 = new IngredientQuantity(ingredients.get(1), 100);
        IngredientQuantity iq3 = new IngredientQuantity(ingredients.get(2), 0.15);
        IngredientQuantity iq4 = new IngredientQuantity(ingredients.get(3), 1);
        List<IngredientQuantity> ingredientQuantities = List.of(iq1, iq2, iq3, iq4);
        Dish dish = new Dish(1, "Hot Dog", 15000.0, ingredientQuantities);
        double expectedCost = 5500.0;
        double actualCost = dish.getCost();

        assertEquals(expectedCost, actualCost, "Le coût total du Hot Dog doit être 5500");
    }



    @Test
    void testFilterByName() throws SQLException {
        List<Ingredient> ingredients = IngredientDAO.findFilteredAndPaginated("Tomato", null, null, null, null, null, null, true, 1, 10);
        assertFalse(ingredients.isEmpty());
        assertTrue(ingredients.stream().allMatch(i -> i.getName().contains("Tomato")));
    }

    @Test
    void testFilterByUnit() throws SQLException {
        List<Ingredient> ingredients = IngredientDAO.findFilteredAndPaginated(null, Unit.U, null, null, null, null, null, true, 1, 10);
        assertFalse(ingredients.isEmpty());
        assertTrue(ingredients.stream().allMatch(i -> i.getUnit() == Unit.U));
    }

    @Test
    void testFilterByPriceRange() throws SQLException {
        List<Ingredient> ingredients = IngredientDAO.findFilteredAndPaginated(null, null, 5.0, 10.0, null, null, null, true, 1, 10);
        assertFalse(ingredients.isEmpty());
        assertTrue(ingredients.stream().allMatch(i -> i.getUnitPrice() >= 5.0 && i.getUnitPrice() <= 10.0));
    }

    @Test
    void testSortByPriceAscending() throws SQLException {
        List<Ingredient> ingredients = IngredientDAO.findFilteredAndPaginated(null, null, null, null, null, null, "unit_price", true, 1, 10);
        assertTrue(ingredients.size() > 1);
        assertTrue(ingredients.get(0).getUnitPrice() <= ingredients.get(1).getUnitPrice());
    }

    @Test
    void testSortByPriceDescending() throws SQLException {
        List<Ingredient> ingredients = IngredientDAO.findFilteredAndPaginated(null, null, null, null, null, null, "unit_price", false, 1, 10);
        assertTrue(ingredients.size() > 1);
        assertTrue(ingredients.get(0).getUnitPrice() >= ingredients.get(1).getUnitPrice());
    }

    @Test
    void testPagination() throws SQLException {
        List<Ingredient> firstPage = IngredientDAO.findFilteredAndPaginated(null, null, null, null, null, null, "id", true, 1, 5);
        List<Ingredient> secondPage = IngredientDAO.findFilteredAndPaginated(null, null, null, null, null, null, "id", true, 2, 5);

        assertEquals(5, firstPage.size());
        assertEquals(5, secondPage.size());
        assertTrue(firstPage.get(4).getId() < secondPage.get(0).getId());
    }
    }



