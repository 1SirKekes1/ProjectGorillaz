package myasnikov.config;

import myasnikov.entity.Quest;
import myasnikov.entity.QuestChoice;
import myasnikov.entity.QuestStep;
import myasnikov.entity.User;
import myasnikov.repository.QuestRepository;
import myasnikov.repository.UserRepository;
import myasnikov.service.QuestService;
import myasnikov.service.UserService;

import java.util.List;

public class DataInitializer {

    public static void initializeData() {
        QuestService questService = new QuestService();
        UserService userService = new UserService();

        User user1 = new User(1L, "john_doe", "john@example.com", "password123");
        userService.save(user1);

        User user2 = new User(2L, "jane_doe", "jane@example.com", "password456");
        userService.save(user2);

        Quest quest1 = new Quest(
                1L,
                "The Lost Treasure",
                "Find the hidden treasure in the ancient ruins.",
                List.of(
                        new QuestStep(
                                1L,
                                "You are standing in front of the ruins. There are two paths: left and right.",
                                List.of(
                                        new QuestChoice(1L, "Go left", 2L),
                                        new QuestChoice(2L, "Go right", 3L)
                                )
                        ),
                        new QuestStep(
                                2L,
                                "You find a dark cave. Do you enter?",
                                List.of(
                                        new QuestChoice(3L, "Enter the cave", 4L),
                                        new QuestChoice(4L, "Go back", 1L)
                                )
                        ),
                        new QuestStep(
                                3L,
                                "You see a shiny object in the distance. Do you approach it?",
                                List.of(
                                        new QuestChoice(5L, "Approach the object", 6L),
                                        new QuestChoice(6L, "Ignore it and continue", 7L)
                                )
                        )
                )
        );

        questService.save(quest1);

        System.out.println("Test data initialized!");
    }
}