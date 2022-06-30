package data;

import java.sql.*;

public class DataSource {
    private static Connection connection;

    private DataSource() {
        try {
            String path = "jdbc:sqlite:src/main/resources/dataBase/applicationDataBase.db";
            connection  = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
           new DataSource();
        }
        return connection;
    }
}