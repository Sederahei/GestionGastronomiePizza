package org.alherendro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found.", e);
        }
    }

    private static final String url = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if (USER == null || PASSWORD == null) {
            throw new SQLException("Database environment variables are not set properly.");
        }
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/lagastronomiepizza", USER, PASSWORD);
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Failed to establish database connection.");
        }
        return conn;
    }
}









