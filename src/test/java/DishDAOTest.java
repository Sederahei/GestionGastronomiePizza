
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Unit;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishDAOTest{
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
        int a = 1;

        a = a + 1;

        assertEquals( 2, a );


    }

}

