import org.alherendro.dao.DishDAO;
import org.alherendro.entity.Dish;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Dish dish = new Dish(1, "hot dog", 15000, List.of());

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
        Dish dish = new Dish(7, "Tsaramaso", 10000.0, List.of());

        // ==> Execution
        dishDAO.save(dish);

        // ==> Verification
        System.out.println("dish succeful saved " + dish);
        assertEquals(7, dish.getId());

    }

//
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
// manimba zavatra dia natao anaty Com's (DELETE)
    //    @Test
    //    void delete_dish_ok() {
    //
    //        // ==> Preparation
    //        DishDAO dishDAO = new DishDAO();
    //        Dish dish = new Dish(1, "Hot dog", 15000.0, List.of());
    //
    //        // ==> Execution
    //        dishDAO.delete(dish);
    //
    //        // ==> Verification
    //        System.out.println(dish);
    //        assertEquals(1, dish.getId());
    //    }



    @Test
    void testGetIngredientsCost() throws SQLException {
        Dish dish = new Dish(1, "HotDog", 6000.0); // Prix de vente fixé

        // Vérifier avec la date actuelle (dernier prix)
        double costToday = dish.getIngredientsCost(LocalDate.now());
        assertEquals(5500.0, costToday, 0.1);

        // Vérifier avec une date antérieure
        double costBefore = dish.getIngredientsCost(LocalDate.of(2025, 1, 1));
        assertTrue(costBefore < costToday);

        // Vérifier avec une date future
        double costAfter = dish.getIngredientsCost(LocalDate.of(2025, 3, 1));
        assertTrue(costAfter > costToday);

        System.out.println("Coût aujourd'hui: " + costToday);
        System.out.println("Coût au 1er janvier: " + costBefore);
        System.out.println("Coût au 1er mars: " + costAfter);
    }


}




