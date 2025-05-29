package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_reservation_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
        
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
