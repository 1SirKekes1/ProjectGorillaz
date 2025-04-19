package myasnikov.config;

import myasnikov.entity.Quest;
import myasnikov.entity.QuestChoice;
import myasnikov.entity.QuestStep;
import myasnikov.entity.User;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(QuestChoice.class);
                configuration.addAnnotatedClass(Quest.class);
                configuration.addAnnotatedClass(QuestStep.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to create Hibernate SessionFactory", e);
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/game");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "create");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        return configuration;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}