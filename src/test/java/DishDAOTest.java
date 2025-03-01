import org.alherendro.dao.DishDAO;
import org.alherendro.entity.Dish;
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
    void calculate_total_ingredient_cost_for_hot_dog() {
        // ==> Préparation (Given)
        DishDAO dishDAO = new DishDAO();
        Dish dish = dishDAO.findById(1);  // Récupère le plat avec ses ingrédients

        // ==> Exécution (When)
        double cost = dish.getCost();

        // ==> Vérification (Then)
        System.out.println("Total cost: " + cost);
        assertEquals(5500.0, cost, 0.01);  // Permet une petite marge d'erreur
    }

}




