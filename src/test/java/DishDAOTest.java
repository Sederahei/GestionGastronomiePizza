
import org.alherendro.DAO.DishDAO;
import org.alherendro.Etinty.Dish;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.IngredientQuantity;
import org.alherendro.Etinty.Unit;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DishDAOTest {

    @Test
    public void testFindById() throws SQLException {
        DishDAO dishDAO = new DishDAO();
        Dish hotDog = dishDAO.findById(1);
        assertNotNull(hotDog);
    }


    @Test
    public void testGetIngredientCost() {
        Ingredient sausage = new Ingredient(1, "Sausage", LocalDateTime.now(), 20.0, Unit.G);
        Ingredient oil = new Ingredient(2, "Oil", LocalDateTime.now(), 10000.0, Unit.L);
        Ingredient egg = new Ingredient(3, "Egg", LocalDateTime.now(), 1000.0, Unit.U);
        Ingredient bread = new Ingredient(4, "Bread", LocalDateTime.now(), 1000.0, Unit.U);

        List<IngredientQuantity> comps = List.of(
                new IngredientQuantity(sausage, 100),
                new IngredientQuantity(oil, 0.15),
                new IngredientQuantity(egg, 1),
                new IngredientQuantity(bread, 1)
        );

        Dish hotDog = new Dish(1, "Hot Dog", 15000.0, comps);
        assertEquals(5500.0, hotDog.getIngredientCost(), 0.001);
    }
}


