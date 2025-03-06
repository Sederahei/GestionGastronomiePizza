import org.alherendro.DataSource;
import org.alherendro.dao.IngredientDAO;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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
        assertTrue(ingredients.size() > 0);
    }


// @Test
//    void testFilterSortPaginateIngredients() {
//        IngredientDAO dao = new IngredientDAO();
//
//        // Test pour filtrer par nom et trier par prix
//        List<Ingredient> ingredients = dao.filterSortPaginateIngredients("Sau", "G", 10.0, 100.0, null, null, "unit_price", 1, 5);
//
//        // Vérification que la liste n'est pas vide
//        assertNotNull(ingredients);
//        System.out.println("votre page est vide  " + ingredients.size());
//        assertTrue(ingredients.size() > 0);
//
//        // Vérifier que le nom contient "Sau"
//        for (Ingredient ingredient : ingredients) {
//            System.out.println("Nom de l'ingrédient non trouvé : " + ingredient.getName());
//            assertTrue(ingredient.getName().contains("Sau"));
//        }
//
//        // Vérifier que l'unité est "G"
//        for (Ingredient ingredient : ingredients) {
//            assertEquals("G", ingredient.getUnit().toString());
//        }
//
//        // Vérifier que les prix sont dans l'intervalle [10.0, 100.0]
//        for (Ingredient ingredient : ingredients) {
//            System.out.println("votre prix que vous avez ecrie est l'intervale de " + ingredient.getUnitPrice());
//            assertTrue(ingredient.getUnitPrice() >= 10.0 && ingredient.getUnitPrice() <= 100.0);
//        }
//
//        assertFalse(ingredients.isEmpty(), "La liste des ingrédients ne doit pas être vide !");
//        for (Ingredient ingredient : ingredients) {
//            System.out.println("Ingredient trouvé : " + ingredient.getName() + ", Unité : " + ingredient.getUnit() + ", Prix : " + ingredient.getUnitPrice());
//        }
//
//
//        // Vérifier que la pagination fonctionne
//        assertEquals(1, ingredients.size()); // On s'attend à 5 éléments dans cette page
//    }
//    }

    @Test
    void testFilterSortPaginateIngredients() {
        IngredientDAO dao = new IngredientDAO();

        try {
            List<Ingredient> ingredients = dao.findFilteredAndPaginated(
                    "Sau", Unit.G, 10.0, 100.0, null, null, "unit_price", true, 1, 5);

            assertNotNull(ingredients);
            System.out.println("Nombre d'ingrédients trouvés : " + ingredients.size());
            assertFalse(ingredients.isEmpty(), "La liste des ingrédients ne doit pas être vide !");

            for (Ingredient ingredient : ingredients) {
                System.out.println("Ingredient trouvé : " + ingredient.getName() + ", Unité : " + ingredient.getUnit() + ", Prix : " + ingredient.getUnitPrice());
                assertTrue(ingredient.getName().contains("Sau"));
                assertEquals(Unit.G, ingredient.getUnit());
                assertTrue(ingredient.getUnitPrice() >= 10.0 && ingredient.getUnitPrice() <= 100.0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            fail("Une erreur SQL s'est produite !");
        }
    }

    @Test
    void testGetIngredientsCost() throws SQLException {
        Dish dish = new Dish(1, "Hot dog", 15000.0);

        // Coût avant la modification des prix des ingrédients
        double costBefore = dish.getIngredientsCost(LocalDate.of(2025, 1, 1));
        assertEquals(5500.0, costBefore, "Le coût doit être 5500");

        System.out.println("Coût des ingredients au 1er janvier: " + costBefore);

        // Modification des prix des ingrédients dans la base et récupération du coût à une autre date
        double costAfter = dish.getIngredientsCost(LocalDate.of(2025, 2, 1));
        System.out.println("Coût après mise à jour des prix: " + costAfter);
    }

}

