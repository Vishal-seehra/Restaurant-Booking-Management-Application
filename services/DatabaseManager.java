package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://restaurant-db.cl80c82c8jen.eu-north-1.rds.amazonaws.com:3306/restaurant_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "admin";
    private static final String PASSWORD = "Seehra04";

    // Method to get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
