package over.achievers.database.SQLServer;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    public Connection getConnection() {
        Connection connection = null;

        // Load Properties File
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/database.properties"));
        } catch (IOException e) {
            // TODO IMPLEMENT LOGGING
        }

        // Connect to Database
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            // TODO IMPLEMENT LOGGING
        }

        // Discard sensitive information after use.
        properties.clear();

        return connection;
    }
}
