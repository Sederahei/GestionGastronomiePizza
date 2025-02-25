package org.alherendro.dao;


import org.alherendro.DataSource;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDAO implements CrudOperations <Ingredient> {

    @Override
    public Ingredient findById(long id) throws SQLException {
        //
        String sql  = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient WHERE id_ingredient = ?";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){

                return new Ingredient(
                        rs.getInt("id_ingredient"),
                        rs.getString("name"),
                        rs.getTimestamp("update_datetime").toLocalDateTime(),
                        rs.getDouble("unit_price"),
                        Unit.valueOf(rs.getString("unit"))
                );
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        throw new UnsupportedOperationException(".....");
    }

    @Override
    public Ingredient save(Ingredient entity) {







        throw new UnsupportedOperationException(".....");
    }

    @Override
    public Ingredient update(Ingredient entity) {







        throw new UnsupportedOperationException(".....");
    }

    @Override
    public Ingredient delete(Ingredient entity) {




        throw new UnsupportedOperationException(".....");
    }
}
