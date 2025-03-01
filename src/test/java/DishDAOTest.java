import org.alherendro.dao.DishDAO;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishDAOTest{
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
    void find_by_id()  {

        // ==> Preparation
        DishDAO dishDAO = new DishDAO();
        Dish dish = new Dish(1, "Hot dog", 15000.0, List.of());

        // ==> Execution
        dishDAO.findById(1);

        // ==> Verification
        System.out.println(dish);
        assertEquals(1, dish.getId());
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


    @Test
    void calculate_total_ingredient_cost_for_hot_dog() {
        // ==> Preparation( give)
        Ingredient ingredient = new Ingredient();
        IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient, 100);
        
        List<IngredientQuantity> ingredients = List.of(ingredientQuantity);
        DishDAO  dishDAO = new DishDAO(); // objet
        Dish dish = dishDAO.findById(1);

        // ==> Execution
        double cost = dish.getCost();

        // ==> Verification
        System.out.println("Total cost: " + cost);
        assertEquals(cost, 5500.0);
        
    }


}

