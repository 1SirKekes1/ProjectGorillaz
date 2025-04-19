package myasnikov.config;

import lombok.Getter;
import myasnikov.dao.QuestChoiceDao;
import myasnikov.dao.QuestDao;
import myasnikov.dao.QuestStepDao;
import myasnikov.dao.UserDao;
import myasnikov.utility.TextToQuestParser;
import org.hibernate.SessionFactory;

import java.io.IOException;

public class AppConfig {

    SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

    @Getter
    static UserDao userDao = new UserDao(HibernateConfig.getSessionFactory());

    @Getter
    static QuestDao questDao = new QuestDao(HibernateConfig.getSessionFactory());

    @Getter
    static QuestStepDao questStepDao = new QuestStepDao(HibernateConfig.getSessionFactory());

    @Getter
    static QuestChoiceDao questChoiceDao = new QuestChoiceDao(HibernateConfig.getSessionFactory());

    private AppConfig() {
        initializeQuests();
    }

    private static final AppConfig appConfig = new AppConfig();

    public static void initializeQuests() {
        try {
            TextToQuestParser parser = new TextToQuestParser("quests","images", questDao, questStepDao, questChoiceDao);
            parser.parse();
        } catch (IOException e) {
            System.err.println("Error at parsing quests from files: " + e.getMessage());
            e.printStackTrace();
        }
    }
}