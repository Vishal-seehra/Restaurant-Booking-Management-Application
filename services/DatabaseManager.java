package services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://restaurant-db.cl80c82c8jen.eu-north-1.rds.amazonaws.com:3306/restaurant_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "Seehra04";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
