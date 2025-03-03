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






    //@Test
    //    void testGetGrossMargin() throws SQLException {
    //        // Créer un plat avec un prix de vente de 6000.0
    //        Dish dish = new Dish(1, "Hot dog", 15000.0);
    //
    //        // Coût des ingrédients à la date actuelle (par exemple 5500.0)
    //        double expectedMarginToday = 15000.0 - 5500.0;
    //
    //        // Vérifier avec la date actuelle
    //        double actualMarginToday = dish.getGrossMargin();
    //        if (Math.abs(actualMarginToday - expectedMarginToday) > 0.1) {
    //            System.out.println("Marge brute calculée pour aujourd'hui : " + actualMarginToday);
    //        }
    //
    //        // Vérifier avec une date antérieure (par exemple 2025-01-01)
    //        double expectedMarginBefore = 15000.0 - 2500.0; // Supposons qu'à cette date, le coût des ingrédients soit de 2500.0
    //        double actualMarginBefore = dish.getGrossMargin(LocalDate.of(2025, 1, 1));
    //        if (Math.abs(actualMarginBefore - expectedMarginBefore) > 0.1) {
    //            System.out.println("Marge brute calculée avant le 2025-01-01 : " + actualMarginBefore);
    //        }
    //        assertEquals(expectedMarginBefore, actualMarginBefore, 0.1);
    //    }


    @Test
    void testGetGrossMargin() throws SQLException {
        Dish dish = new Dish(1, "Hot dog", 15000.0);  // Prix de vente fixé à 6000

        // Calculer la marge brute avec la date actuelle (coût des ingrédients le plus récent)
        double marginToday = dish.getGrossMargin(LocalDate.now());
        assertTrue(marginToday > 0, "La marge brute d'aujourd'hui doit être positive");

        // Calculer la marge brute avec une date antérieure (prix des ingrédients du 1er janvier 2025)
        double marginBefore = dish.getGrossMargin(LocalDate.of(2025, 1, 1));
        assertTrue(marginBefore < marginToday, "La marge brute avant le 1er janvier devrait être inférieure");

        // Calculer la marge brute avec une date future (prix des ingrédients du 1er mars 2025)
        double marginAfter = dish.getGrossMargin(LocalDate.of(2025, 2, 1));
        assertTrue(marginAfter > marginToday, "La marge brute après le 1er mars devrait être supérieure");

        // Afficher les résultats pour la vérification
        System.out.println("Marge brute aujourd'hui: " + marginToday);
        System.out.println("Marge brute au 1er janvier: " + marginBefore);
        System.out.println("Marge brute au 1er fevrier: " + marginAfter);
    }





}




