package infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que se encarga de la conexión a la base de datos
 */
public class DataBaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String user = "root";
    private static final String password = "root1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get connection", e);
        }
    }
}
