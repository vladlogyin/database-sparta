package over.achievers.database.SQLServer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;

public class ConnectionFactory {

    private static Properties config;

    public static void loadConfig(String path) throws FileNotFoundException, SecurityException{
        if(config==null)
        {
            config = new Properties();
        }

        File configFile = new File(path);
        synchronized (config) {
            if (!configFile.exists()) {
                throw new FileNotFoundException("Database config file '" + path + "' not found!");
            }
            try {
                config.load(new FileReader(path));
            } catch (IOException ex) {
                // genuinely not sure what this does here
                throw new RuntimeException(ex);
            }
        }

    }
    public static void setConfig(String url, String uname, String passwd) throws NullPointerException
    {
        if(url==null || uname == null || passwd == null)
        {
            throw new NullPointerException("One or more parameters are null");
        }
        if(config==null)
        {
            config = new Properties();
        }
        synchronized (config) {
            config.setProperty("db.url", url);
            config.setProperty("db.username", uname);
            config.setProperty("db.password", passwd);
        }
    }


    public Connection getConnection() throws SQLException{
        if(config==null)
        {
            throw new SQLException("No valid database config!");
        }
        synchronized (config) {
            return DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.username"),
                    config.getProperty("db.password"));
        }
    }
}
