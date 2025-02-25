

import org.alherendro.dao.DishDAO;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishDAOTest{
   // new DishDAO(); ==> tokiy ho io o retur
    @Test
    public void testGetIngredientCost() {
        Ingredient sausage = new Ingredient(1, "Sausage", LocalDateTime.now(), 20.0, Unit.G);
        Ingredient oil = new Ingredient(2, "Oil", LocalDateTime.now(), 10000.0, Unit.L);
        List<IngredientQuantity> comps = getIngredientQuantities(LocalDateTime.now(), LocalDateTime.now(), sausage, oil);

        Dish hotDog = new Dish(1, "Hot Dog", 15000.0, comps);
        assertEquals(5500.0, hotDog.getIngredientCost(), 0.001);
    }


   // @Test
    // void hot_dog_const_ingredient_55000() throws Exception {
    //        int a = 1;
    //
    //        a = a + 1;
    //
    //        assertEquals( 2, a );
    //
    //
    //    }

    @Test
    void hot_dog_const_ingredient_55000() throws Exception {

        // Données de test
        Ingredient sausage = new Ingredient(1, "Sausage", LocalDateTime.of(2025, 1, 1, 0, 0), 20.0, Unit.G);
        Ingredient oil = new Ingredient(2, "Oil", LocalDateTime.of(2025, 1, 1, 0, 0), 10000.0, Unit.L);
        List<IngredientQuantity> components = getIngredientQuantities(LocalDateTime.of(2025, 1, 1, 0, 0), LocalDateTime.of(2025, 1, 1, 0, 0), sausage, oil);

        // Création du plat Hot Dog
        Dish hotDog = new Dish(1, "Hot Dog", 15000.0, components);

        // Vérification du coût total attendu (5500 Ar)
        System.out.println(hotDog.getIngredientCost());
        assertEquals(5500.0, hotDog.getIngredientCost(), 0.001,
                "Le coût total des ingrédients du Hot Dog doit être 5500 Ar.");

    }


    static List<IngredientQuantity> getIngredientQuantities(LocalDateTime of, LocalDateTime of1, Ingredient sausage, Ingredient oil) {
        Ingredient egg = new Ingredient(3, "Egg", of, 1000.0, Unit.U);
        Ingredient bread = new Ingredient(4, "Bread", of1, 1000.0, Unit.U);

        // Composition du plat
        List<IngredientQuantity> components = List.of(
                new IngredientQuantity(sausage, 100),    // 20 * 100 = 2000
                new IngredientQuantity(oil, 0.15),       // 10000 * 0.15 = 1500
                new IngredientQuantity(egg, 1),          // 1000 * 1 = 1000
                new IngredientQuantity(bread, 1)         // 1000 * 1 = 1000
        );
        return components;
    }

    @Test
    void find_all_dishes_ok() throws SQLException {



        // ==> Preparation
        DishDAO dishDAO = new DishDAO();

        // ==> Execution
        Dish dish = dishDAO.findById(1);

        // ==> Verification
        System.out.println(dish);
        assertEquals("Hot dog", dish.getName());
    }


    @Test
    void
    save_dish_ok()  {

        // ==> Preparation
        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish(1, "Hot dog", 15000.0, List.of());

        // ==> Execution
        dishDAO.save(dish);

        // ==> Verification
        System.out.println(dish);
        assertEquals(1, dish.getId());
    }


    @Test
    void
    update_dish_ok()  {

        // ==> Preparation
        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish(1, "Hot dog", 15000.0, List.of());

        // ==> Execution
        dishDAO.update(dish);

        // ==> Verification
        System.out.println(dish);
        assertEquals(1, dish.getId());



    }

    @Test
    void delete_dish_ok() {

        // ==> Preparation
        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish(1, "Hot dog", 15000.0, List.of());

        // ==> Execution
        dishDAO.delete(dish);

        // ==> Verification
        System.out.println(dish);
        assertEquals(1, dish.getId());
    }
}

