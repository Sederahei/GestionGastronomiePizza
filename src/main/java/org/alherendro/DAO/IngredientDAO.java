package org.alherendro.DAO;

import Resource.DataSource;
import org.alherendro.Etinty.Ingredient;
import org.alherendro.Etinty.Unit;
import org.flywaydb.core.internal.database.base.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    public List<Ingredient> filterSortPaginate(String namePattern, Unit unit, double priceMin, double priceMax,
                                               LocalDateTime dateMin, LocalDateTime dateMax, int limit, int offset) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM Ingredient WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (namePattern != null) {
            query.append(" AND name ILIKE ?");
            params.add("%" + namePattern + "%");
        }
        if (unit != null) {
            query.append(" AND unit = ?");
            params.add(unit.name());
        }
        if (priceMin >= 0 && priceMax >= 0) {
            query.append(" AND unit_price BETWEEN ? AND ?");
            params.add(priceMin);
            params.add(priceMax);
        }
        if (dateMin != null && dateMax != null) {
            query.append(" AND update_datetime BETWEEN ? AND ?");
            params.add(Timestamp.valueOf(dateMin));
            params.add(Timestamp.valueOf(dateMax));
        }
        query.append(" ORDER BY name ASC, unit_price DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        try (Connection conn = (Connection) DataSource.getConnection();
             PreparedStatement stmt = conn.getJdbcConnection().prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            List<Ingredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                ingredients.add(new Ingredient(
                        rs.getInt("id_ingredient"),
                        rs.getString("name"),
                        rs.getTimestamp("update_datetime").toLocalDateTime(),
                        rs.getDouble("unit_price"),
                        Unit.valueOf(rs.getString("unit"))
                ));
            }
            return ingredients;
        }
    }
}
