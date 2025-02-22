
import Resource.DataSource;
import org.alherendro.DAO.CrudOperations;
import org.alherendro.DAO.DishDAO;
import org.alherendro.Etinty.Dish;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.IngredientQuantity;
import org.alherendro.Etinty.Unit;
import org.flywaydb.core.internal.database.base.Connection;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DishDAOTest implements CrudOperations<Dish> {


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


    @Test
    void hot_dog_const_ingredient_55000() throws Exception {
        double expectedCost = 55000.0;
        try (Connection connection = (Connection) DataSource.getConnection()) {
            DishDAO dishDAO = new DishDAO();
            Dish hotDog = dishDAO.findDishById(1);
            assertNotNull(hotDog);
            assertEquals(expectedCost, hotDog.getIngredientCost(), 0.01);
        }

    }


    @Override
    public Dish findById(long id)  {

        throw new UnsupportedOperationException("mbola tsy implemente satry sody null, verife kely we...");

    }

    @Override
    public Dish save(Dish entity) {

        throw new UnsupportedOperationException("mbola tsy implemente satry sody null");
    }
}


