package data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Objects;

public class DataSource {
    private static Connection connection;

    private DataSource() {
        try {

            Path dataBasePath = Path.of(System.getProperty("user.home"), ".Skynet", "applicationDataBase.db");
            String resourceName= "/dataBase/applicationDataBase.db";

            if (!Files.exists(dataBasePath)) {
                Files.createDirectories(dataBasePath.getParent());
                try(InputStream inputStream = getClass().getResourceAsStream(resourceName)) {
                    Objects.requireNonNull(inputStream, "Not found resource: " + resourceName);
                    Files.copy(inputStream, dataBasePath);
                }
            }

            connection = DriverManager.getConnection("jdbc:sqlite:" + dataBasePath.toAbsolutePath());

        } catch (SQLException | IOException e) {
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