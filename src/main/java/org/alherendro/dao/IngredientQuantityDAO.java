package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.Interface.CrudOperationIngredientQuantity;
import org.alherendro.entity.IngredientQuantity;

import java.sql.*;
import java.time.LocalDateTime;
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
                                IngredientDAO.findByid(rs.getInt("id_ingredient")),
                                
                                rs.getInt("id"),
                                
                                rs.getDouble("required_quantity")
                        )
                );

            }
            return ingredientQuantities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public double getAvailableQuantity(int ingredientId, LocalDateTime date) {
        String query = "SELECT SUM(quantity) FROM stock_movement " +
                "WHERE id_ingredient = ? AND movement_datetime <= ? " +
                "GROUP BY id_ingredient";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ingredientId);
            stmt.setTimestamp(2, Timestamp.valueOf(date));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            } else {
                return 0.0; // Aucun mouvement trouvé
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'état du stock", e);
        }
    }


}
