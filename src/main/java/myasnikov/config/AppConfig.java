package myasnikov.config;

import lombok.Getter;
import myasnikov.entity.Quest;
import myasnikov.entity.User;
import myasnikov.repository.QuestRepository;
import myasnikov.repository.Repository;
import myasnikov.repository.UserRepository;

public class AppConfig {

    private AppConfig() {}

    @Getter
    private static final Repository<Quest> questRepository = new QuestRepository();
    @Getter
    private static final Repository<User> userRepository = new UserRepository();

}