package org.alherendro.dao;
import org.alherendro.DataSource;
import org.alherendro.Interface.CrudOperationsDish;
import org.alherendro.entity.Dish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.IngredientQuantity;
import org.alherendro.entity.Unit;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DishDAO implements CrudOperationsDish<Dish> {

    public Dish findById(int id) {
        String sql = "SELECT d.id_dish, d.name, di.id_ingredient, di.required_quantity, " +
                "i.name AS ingredient_name, i.unit_price, i.unit " +
                "FROM Dish d " +
                "LEFT JOIN Dish_Ingredient di ON d.id_dish = di.id_dish " +
                "LEFT JOIN Ingredient i ON di.id_ingredient = i.id_ingredient " +
                "WHERE d.id_dish = ?";

        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Dish dish = null;
            List<IngredientQuantity> ingredientQuantities = new ArrayList<>();

            while (rs.next()) {
                // Créer le plat seulement à la première itération
                if (dish == null) {
                    dish = new Dish(
                            rs.getInt("id_dish"),
                            rs.getString("name"),
                            rs.getDouble("unit_price"),
                            ingredientQuantities
                    );
                }

                // Ajouter les ingrédients
                Ingredient ingredient = new Ingredient(
                        rs.getInt("id_ingredient"),
                        rs.getString("ingredient_name"),
                        rs.getDouble("unit_price"),
                        Unit.valueOf(rs.getString("unit")) // Convertir correctement l'ENUM
                );

                IngredientQuantity ingredientQuantity = new IngredientQuantity(
                        ingredient,
                        rs.getDouble("required_quantity")
                );

                ingredientQuantities.add(ingredientQuantity);
            }

            return dish;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dish save(Dish entity)                                 {
        String sql = "INSERT INTO dish (name, unit_price) VALUES (?, ?)";
        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("id_dish"));
                entity.setName(rs.getString("name"));
                entity.setUnitPrice(rs.getDouble("unit_price"));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<Ingredient> getAll() {
        return List.of();
    }

    @Override
    public List<Ingredient> filterSortPaginateIngredients(String sau, String g, double v, double v1, Object o, Object o1, String unitPrice, int i, int i1) {
        return List.of();
    }

    @Override
    public Dish update(Dish entity) {
        String sql = "UPDATE dish SET name = ?, unit_price = ? WHERE id_dish = ?";
        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getUnitPrice());
            stmt.setLong(3, entity.getId());
            stmt.executeUpdate(); // afaka tsy mampiasa  rs satry type de retour ny Update Int
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return new Dish();

    }


    @Override
    public Dish delete(Dish entity) {


        String sql = "DELETE FROM dish WHERE id_dish = ?";

        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, entity.getId());
            stmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }


}
//  public Dish findDishById(int dishId) throws SQLException {
//        String dishQuery = "SELECT * FROM dish WHERE id_dish = ?";
//        String ingredientQuery = "SELECT i.id_ingredient, i.name, i.price, i.unit, di.required_quantity " +
//                "FROM dish_ingredient di JOIN ingredient i ON di.id_ingredient = i.id_ingredient WHERE di.id_dish = ?";
//
//        try {
//            Connection connection = DataSource.getConnection();
//            PreparedStatement dishStmt = connection.prepareStatement(dishQuery);
//            PreparedStatement ingredientStmt = connection.prepareStatement(ingredientQuery);
//            dishStmt.setInt(1, dishId);
//            ResultSet dishRs = dishStmt.executeQuery();
//            if (dishRs.next()) {
//                String name = dishRs.getString("name");
//                double price = dishRs.getDouble("price");
//                List<IngredientQuantity> ingredientQuantities = new ArrayList<>();
//                ingredientStmt.setInt(1, dishId);
//                ResultSet ingRs = ingredientStmt.executeQuery();
//                while (ingRs.next()) {
//                    Ingredient ingredient = new Ingredient(
//                            ingRs.getInt("id_ingredient"),
//                            ingRs.getString("name"),
//                            null,
//                            ingRs.getDouble("price"),
//                            Unit.valueOf(ingRs.getString("unit"))
//                    );
//                    double quantity = ingRs.getDouble("required_quantity");
//                    ingredientQuantities.add(new IngredientQuantity(ingredient, quantity));
//
//
//                return new Dish(dishId, name, price, ingredientQuantities);
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        throw new SQLException("No dish found with id: " + dishId);
//    }
//}


