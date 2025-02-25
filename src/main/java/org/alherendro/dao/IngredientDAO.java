package org.alherendro.dao;
import org.alherendro.DataSource;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.Unit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class IngredientDAO implements CrudOperations <Ingredient> {

  //  ==>  tokiny ho  hisy optionly eto ( si null , si return id)

    @Override
    public   Ingredient findById(long id) throws SQLException {
        Ingredient ingredient= new Ingredient();
        String sql  = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient WHERE id_ingredient = ?";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                // maka id any @ base de donne ( set : maovaId(rs.getInt("id_ingredient("zavatra ho soloina azy any db")))
                ingredient.setId(rs.getInt("id_ingredient"));
                ingredient.setName(rs.getString("name"));
                ingredient.setUpdateDatetime(rs.getTimestamp("update_datetime").toLocalDateTime());
                ingredient.setUnitPrice(rs.getDouble("unit_price"));
                ingredient.setUnit(Unit.valueOf(rs.getString("unit")));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return  ingredient; // returne variable ingredient fa tsy classe
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



    public Ingredient getAll() {

        String sql = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient";
        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeQuery();
            ResultSet rs = stmt.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();

            //==> rs.next : raha mbola misy ligne dia alaivo ny @ base
            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getInt("id_ingredient"),
                        rs.getString("name"),
                        rs.getTimestamp("update_datetime").toLocalDateTime(),
                        rs.getDouble("unit_price"),
                        Unit.valueOf(rs.getString("unit"))
                ));
            }
            return (Ingredient) ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ingredient hot_dog_const_ingredient_55000() {
        return new Ingredient(
                137, "hot dog", LocalDateTime.of(2021, 5, 10, 0, 0), 55000, Unit.G);
    }
}
