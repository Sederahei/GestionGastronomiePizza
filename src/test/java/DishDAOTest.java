

import Resource.DataSource;


import org.alherendro.DAO.DishDAO;
import org.alherendro.Etinty.Dish;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DishDAOTest {

    @Test
    public void testGetIngredientCost() throws Exception {
        try (Connection connection = DataSource.getConnection()) {

        }
    }


