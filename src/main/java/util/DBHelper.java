package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import exception.DBException;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
    private static DBHelper dbHelper;
    private  Connection connection;
    private  Configuration configuration;

    private DBHelper() {
    }
    public static DBHelper getInstance(){
        if(dbHelper == null){
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    public  Connection getMysqlConnection() {
        if(connection == null) {
            Path propFile = Paths.get("C:\\ideaProj\\jm_study\\part1\\src\\main\\resources\\db.properties");
            Properties properties = PropertyReader.getProperties(propFile.toAbsolutePath().toString());
            try {
                Class.forName(properties.getProperty("driverSQL"));
                connection=  DriverManager.getConnection(properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password"));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException();
            }
        }
        return connection;
    }

    public Configuration getMySqlConfiguration() {
        configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        Path propFile = Paths.get("C:\\ideaProj\\jm_study\\part1\\src\\main\\resources\\db.properties");
        Properties properties = PropertyReader.getProperties(propFile.toAbsolutePath().toString());

        configuration.setProperty("hibernate.dialect", properties.getProperty("dialect"));
        configuration.setProperty("hibernate.connection.driver_class", properties.getProperty("driverSQL"));
        configuration.setProperty("hibernate.connection.url", properties.getProperty("url"));
        configuration.setProperty("hibernate.connection.username", properties.getProperty("username"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("password"));
        configuration.setProperty("hibernate.show_sql", properties.getProperty("show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hbm2ddl.auto"));
        return configuration;
    }

}
