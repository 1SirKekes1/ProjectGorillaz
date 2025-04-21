package myasnikov.config;

import lombok.Getter;
import myasnikov.dao.QuestChoiceDao;
import myasnikov.dao.QuestDao;
import myasnikov.dao.QuestStepDao;
import myasnikov.dao.UserDao;

import myasnikov.service.UserService;
import myasnikov.utility.TextToQuestParser;



import java.io.IOException;

public class AppConfig {

    private static AppConfig instance;

    @Getter
    static UserDao userDao = new UserDao(HibernateConfig.getSessionFactory());

    static UserService userService = new UserService();

    @Getter
    static QuestDao questDao = new QuestDao(HibernateConfig.getSessionFactory());

    @Getter
    static QuestStepDao questStepDao = new QuestStepDao(HibernateConfig.getSessionFactory());

    @Getter
    static QuestChoiceDao questChoiceDao = new QuestChoiceDao(HibernateConfig.getSessionFactory());

    public AppConfig() {
        initializeQuests();
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public static void initializeQuests() {
        try {
            TextToQuestParser parser = new TextToQuestParser(
                    "quests",
                    "images",
                    questDao,
                    questStepDao,
                    questChoiceDao);
            parser.parse();
        } catch (IOException e) {
            System.err.println("Error at parsing quests from files: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void generateTestUsers() {
        userService.createUser("test", "john@example.com", "test", 10L, 7L, 3L);
        userService.createUser("jane_smith", "jane@example.com", "securepass", 15L, 10L, 5L);
        userService.createUser("alex_wong", "alex@example.com", "alex1234", 8L, 5L, 3L);
        userService.createUser("sara_johnson", "sara@example.com", "saraPass", 20L, 15L, 5L);
        userService.createUser("mike_brown", "mike@example.com", "mikeTheBest", 12L, 8L, 4L);
    }
}