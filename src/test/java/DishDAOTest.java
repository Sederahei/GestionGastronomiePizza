

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


    @Test
    public void hot_dog_const_ingredient_55000() throws Exception {
        Ingredient ingredient = new Ingredient(1, "Hot dog", LocalDateTime.now(), 55000.0, Unit.G);

        List<IngredientQuantity> ingredients = List.of(new IngredientQuantity(ingredient, 1.0));
        Dish dish = new Dish(1, "Hot dog", 15000.0, ingredients);

        System.out.println(IngredientQuantity.class.isAssignableFrom(dish.hot_dog_const_ingredient_55000().getClass()));
        List<Ingredient> list = ingredients.stream().map(IngredientQuantity::getIngredient).toList(); List.of(55000.0, dish.hot_dog_const_ingredient_55000().getClass());


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

