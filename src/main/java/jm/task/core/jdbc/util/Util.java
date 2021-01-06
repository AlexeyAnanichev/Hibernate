package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


import java.sql.*;
import java.util.Properties;

public class Util {

    private Connection connection;
    private SessionFactory sessionFactory = buildSessionFactory();
    private ServiceRegistry serviceRegistry;
    private final String url = "jdbc:mysql://localhost:3306/new_db?serverTimezone=Europe/Moscow&useSSL=false";
    private final String logPas = "root";

    public void connectToDB() {
        try {
            this.connection = DriverManager.getConnection(url, logPas, logPas);
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            System.err.println(e);
        }
    }

    public SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.URL, url);
            properties.put(Environment.USER, logPas);
            properties.put(Environment.PASS, logPas);
            properties.put(Environment.SHOW_SQL, "true");
            config.setProperties(properties).addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    config.getProperties()).build();
            sessionFactory = config.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.err.println(e);
        }
        return sessionFactory;
    }

    public Connection getConnection() {
        return connection;
    }
}
