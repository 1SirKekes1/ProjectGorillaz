package myasnikov.utility;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import myasnikov.config.AppConfig;
import myasnikov.config.HibernateConfig;



@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppConfig.initializeQuests();
        AppConfig.generateTestUsers();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateConfig.getSessionFactory().close();
    }
}
