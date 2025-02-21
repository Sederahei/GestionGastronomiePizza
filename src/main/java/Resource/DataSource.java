package Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private final Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DataSource()  {
        String url = "jdbc:postgresql://localhost:5432/pizza";
        String user = System.getenv("DATABASE_USER") ;
        String password = System.getenv("DATABASE_PASSWORD");

        if (user == null || password == null) {
            throw new IllegalStateException("Les variables d'environnement DATABASE_USER et DATABASE_PASSWORD doivent être définies.");
        }
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}


