package Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://DESKTOP-GOCQ91T:1433;"
                                    + "databaseName=StudentDB;"
                                    + "encrypt=false;"
                                    + "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}