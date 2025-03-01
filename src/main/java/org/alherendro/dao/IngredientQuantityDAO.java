package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.Interface.CrudOperationIngredientQuantity;
import org.alherendro.entity.IngredientQuantity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientQuantityDAO implements CrudOperationIngredientQuantity<IngredientQuantity> {

    @Override
    public List<IngredientQuantity> findByDishId() {
        String sql = "SELECT id , id_ingredient ,requiride_quantity,unit FROM ingredient_quantity";
        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            List<IngredientQuantity> ingredientQuantities = new ArrayList<>();
            while (rs.next()) {

                ingredientQuantities.add(
                new IngredientQuantity(
                    IngredientDAO.findByid(rs.getInt("id")),
                    rs.getInt("id_ingredient"),
                    rs.getDouble("requiride_quantity")
                ));


            }
            return ingredientQuantities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
