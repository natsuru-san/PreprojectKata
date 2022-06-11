package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {

    private static SessionFactory sessionFactory = null;
    private static final String URL = "jdbc:mysql://localhost/hibernate?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER_NAME = "natsuru";
    private static final String PASS_KEY = "1234";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration()
                        .setProperty(Environment.DRIVER, DRIVER)
                        .setProperty(Environment.URL, URL)
                        .setProperty(Environment.USER, USER_NAME)
                        .setProperty(Environment.PASS, PASS_KEY)
                        .setProperty(Environment.DIALECT, DIALECT)
                        .addAnnotatedClass(User.class);
                ServiceRegistry service = new StandardServiceRegistryBuilder()
                        .applySettings(cfg.getProperties())
                        .build();
                sessionFactory = cfg.buildSessionFactory(service);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
