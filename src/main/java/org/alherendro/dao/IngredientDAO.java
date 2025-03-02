package org.alherendro.dao;

import org.alherendro.DataSource;
import org.alherendro.Interface.CrudOperationsDish;
import org.alherendro.entity.Ingredient;
import org.alherendro.entity.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class IngredientDAO implements CrudOperationsDish<Ingredient> {


    private static Ingredient mapResultSetToIngredient(ResultSet rs) throws SQLException {
    return new Ingredient(
            rs.getInt("id_ingredient"),
            rs.getString("name"),
            rs.getTimestamp("update_datetime").toLocalDateTime(),
            rs.getDouble("unit_price"),
            Unit.valueOf(rs.getString("unit"))
    );
}
    public static Ingredient findByid(int id){
        Ingredient ingredient = new Ingredient();
        String sql = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient WHERE id_ingredient = ?";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // maka id any @ base de donne ( set : maovaId(rs.getInt("id_ingredient("zavatra ho soloina azy any db")))
                ingredient.setId(rs.getInt("id_ingredient"));
                ingredient.setName(rs.getString("name"));
                ingredient.setUpdateDatetime(rs.getTimestamp("update_datetime").toLocalDateTime());
                ingredient.setUnitPrice(rs.getDouble("unit_price"));
                ingredient.setUnit(Unit.valueOf(rs.getString("unit")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    public static int setId(int idIngredient) {
        return idIngredient;
    }

    //  ==>  tokiny ho  hisy optionly eto ( si null , si return id)

    @Override
    public Ingredient findById(int id) {
        Ingredient ingredient = new Ingredient();
        String sql = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient WHERE id_ingredient = ?";
        try {

            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // maka id any @ base de donne ( set : maovaId(rs.getInt("id_ingredient("zavatra ho soloina azy any db")))
                ingredient.setId(rs.getInt("id_ingredient"));
                ingredient.setName(rs.getString("name"));
                ingredient.setUpdateDatetime(rs.getTimestamp("update_datetime").toLocalDateTime());
                ingredient.setUnitPrice(rs.getDouble("unit_price"));
                ingredient.setUnit(Unit.valueOf(rs.getString("unit")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient; // returne variable ingredient fa tsy classe
    }

    public Ingredient save(Ingredient entity) {
        String sql = "INSERT INTO ingredient (name, update_datetime, unit_price, unit) VALUES (?, ?, ?, ?::unit)";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getUpdateDatetime()));
            stmt.setDouble(3, entity.getUnitPrice());
            stmt.setObject(4, entity.getUnit().name(), java.sql.Types.OTHER);

            stmt.executeUpdate();

            // Récupération de l'ID généré
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de l'ingrédient", e);
        }
        return entity;
    }


    @Override
    public Ingredient update(Ingredient entity) {
        String sql = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient WHERE id_ingredient = ?";
        try {
            Connection connection = DataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, entity.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("id_ingredient"));
                entity.setName(rs.getString("name"));
                entity.setUpdateDatetime(rs.getTimestamp("update_datetime").toLocalDateTime());
                entity.setUnitPrice(rs.getDouble("unit_price"));
                entity.setUnit(Unit.valueOf(rs.getString("unit")));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Ingredient delete(Ingredient entity) {


        throw new UnsupportedOperationException(".....");
    }


    public List<Ingredient> getAll() {
        String sql = "SELECT id_ingredient, name, update_datetime, unit_price, unit FROM ingredient";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Ingredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                ingredients.add(mapResultSetToIngredient(rs));
            }
            return ingredients;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ingrédients", e);
        }
    }



    @Override
    public List<Ingredient> filterSortPaginateIngredients(String sau, String g, double v, double v1, Object o, Object o1, String unitPrice, int page, int pageSize) {
        // Conversion des paramètres en valeurs appropriées pour la méthode findFilteredAndPaginated
        Unit unit = null;
        if (g != null) {
            unit = Unit.valueOf(g); // suppose que g est un code pour l'unité
        }

        // Appeler la méthode findFilteredAndPaginated avec les bons paramètres
        try {
            return findFilteredAndPaginated(sau, unit, v, v1, null, null, unitPrice, true, page, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList(); // retourner une liste vide en cas d'erreur
        }
    }

    // ==> Q5


        public List<Ingredient> findFilteredAndPaginated(
                String name, Unit unit, Double minPrice, Double maxPrice,
                Timestamp minDate, Timestamp maxDate, String sortBy, boolean asc,
                int page, int pageSize) throws SQLException {

            List<Ingredient> ingredients = new ArrayList<>();
            StringBuilder sql = new StringBuilder("SELECT * FROM ingredient WHERE 1=1 ");

            // Ajout des filtres dynamiques
            if (name != null) sql.append(" AND name ILIKE ? ");
            if (unit != null) sql.append(" AND unit = ?::unit ");
            if (minPrice != null) sql.append(" AND unit_price >= ? ");
            if (maxPrice != null) sql.append(" AND unit_price <= ? ");
            if (minDate != null) sql.append(" AND update_datetime >= ? ");
            if (maxDate != null) sql.append(" AND update_datetime <= ? ");

            // Ajout du tri
            if (sortBy != null) {
                sql.append(" ORDER BY ").append(sortBy).append(asc ? " ASC " : " DESC ");
            }

            // Ajout de la pagination
            sql.append(" LIMIT ? OFFSET ?");

            try (Connection connection = DataSource.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(sql.toString())) {

                int index = 1;

                // Remplissage des paramètres dynamiques
                if (name != null) stmt.setString(index++, "%" + name + "%");
                if (unit != null) stmt.setObject(index++, unit.name(), Types.OTHER);
                if (minPrice != null) stmt.setDouble(index++, minPrice);
                if (maxPrice != null) stmt.setDouble(index++, maxPrice);
                if (minDate != null) stmt.setTimestamp(index++, minDate);
                if (maxDate != null) stmt.setTimestamp(index++, maxDate);

                // Ajout de la pagination (protection contre les valeurs négatives)
                stmt.setInt(index++, pageSize);
                stmt.setInt(index, Math.max(0, (page - 1) * pageSize));

                // Exécution de la requête et récupération des résultats
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    ingredients.add(mapResultSetToIngredient(rs));
                }
            }

            return ingredients;
        }
    }



    