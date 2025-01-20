package myasnikov.config;

import lombok.Getter;
import myasnikov.entity.Quest;
import myasnikov.entity.QuestChoice;
import myasnikov.entity.QuestStep;
import myasnikov.entity.User;
import myasnikov.repository.QuestRepository;
import myasnikov.repository.Repository;
import myasnikov.repository.UserRepository;

import java.util.List;

public class AppConfig {

  private AppConfig() {
    initializeQuestTestData();
    initializeUserTestData();
  }

  @Getter private static final Repository<Quest> questRepository = new QuestRepository();
  @Getter private static final Repository<User> userRepository = new UserRepository();

  public static void initializeQuestTestData() {
    Quest quest1 =
        new Quest(
            1L,
            "The Lost Treasure",
            "Find the hidden treasure in the ancient ruins.",
            List.of(
                new QuestStep(
                    1L,
                    "You are standing in front of the ruins. There are two paths: left and right.",
                    List.of(
                        new QuestChoice(1L, "Go left", 2L), new QuestChoice(2L, "Go right", 2L)),
                    "/images/number1.jpg"),
                new QuestStep(
                    2L,
                    "You find a dark cave. Do you enter?",
                    List.of(
                        new QuestChoice(3L, "Enter the cave", 3L),
                        new QuestChoice(4L, "Go back", 1L)),
                    "/images/number2.jpg"),
                new QuestStep(
                    3L,
                    "You see a shiny object in the distance. Do you approach it?",
                    List.of(
                        new QuestChoice(5L, "Approach the object", 4L),
                        new QuestChoice(6L, "Ignore it and continue", 5L)),
                    "/images/number3.jpg"),
                new QuestStep(4L, "You found diamonds", "/images/treasure.jpg", true),
                new QuestStep(5L, "You lost", "/images/number3.jpg", false)),
            "/images/treasure.jpg");
    questRepository.save(quest1);

    Quest quest2 =
        new Quest(
            2L,
            "Rescue the Princess",
            "Save the princess from the dragon's lair.",
            List.of(
                new QuestStep(
                    4L,
                    "You arrive at the dragon's lair. Do you enter?",
                    List.of(
                        new QuestChoice(7L, "Enter the lair", 5L),
                        new QuestChoice(8L, "Look for another way", 6L)),
                    "/images/treasure.jpg"),
                new QuestStep(
                    5L,
                    "You see the dragon sleeping. Do you attack or sneak past?",
                    List.of(
                        new QuestChoice(9L, "Attack the dragon", 7L),
                        new QuestChoice(10L, "Sneak past", 8L)),
                    "/images/treasure.jpg"),
                new QuestStep(
                    6L,
                    "You find a secret passage. Do you take it?",
                    List.of(
                        new QuestChoice(11L, "Take the passage", 9L),
                        new QuestChoice(12L, "Return to the entrance", 4L)),
                    "/images/treasure.jpg")),
            "/images/dragon.jpg");
    questRepository.save(quest2);

    Quest quest3 =
        new Quest(
            3L,
            "The Cursed Forest",
            "Navigate through the cursed forest to find the ancient artifact.",
            List.of(
                new QuestStep(
                    7L,
                    "You enter the forest. The path splits into three. Which way do you go?",
                    List.of(
                        new QuestChoice(13L, "Take the left path", 8L),
                        new QuestChoice(14L, "Take the middle path", 9L),
                        new QuestChoice(15L, "Take the right path", 10L)),
                    "/images/forest.jpg"),
                new QuestStep(
                    8L,
                    "You encounter a river. Do you swim across or look for a bridge?",
                    List.of(
                        new QuestChoice(16L, "Swim across", 11L),
                        new QuestChoice(17L, "Look for a bridge", 12L)),
                    "/images/forest.jpg"),
                new QuestStep(
                    9L,
                    "You find a mysterious altar. Do you interact with it?",
                    List.of(
                        new QuestChoice(18L, "Interact with the altar", 13L),
                        new QuestChoice(19L, "Ignore it and continue", 14L)),
                    "/images/forest.jpg")),
            "/images/forest.jpg");
    questRepository.save(quest3);

    Quest quest4 =
        new Quest(
            4L,
            "The Pyramid's Riddle",
            "Solve the riddles of the ancient pyramid to unlock its secrets.",
            List.of(
                new QuestStep(
                    10L,
                    "You stand before the pyramid entrance. Do you enter?",
                    List.of(
                        new QuestChoice(20L, "Enter the pyramid", 11L),
                        new QuestChoice(21L, "Search the surroundings", 12L)),
                    "/images/pyramid.jpg"),
                new QuestStep(
                    11L,
                    "You find a room with three doors. Which one do you choose?",
                    List.of(
                        new QuestChoice(22L, "Left door", 13L),
                        new QuestChoice(23L, "Middle door", 14L),
                        new QuestChoice(24L, "Right door", 15L)),
                    "/images/pyramid.jpg"),
                new QuestStep(
                    12L,
                    "You encounter a riddle. Do you try to solve it?",
                    List.of(
                        new QuestChoice(25L, "Attempt to solve the riddle", 16L),
                        new QuestChoice(26L, "Ignore the riddle", 17L)),
                    "/images/pyramid.jpg")),
            "/images/pyramid.jpg");
    questRepository.save(quest4);

    Quest quest5 =
        new Quest(
            5L,
            "The Dungeon of Darkness",
            "Escape the dungeon filled with traps and monsters.",
            List.of(
                new QuestStep(
                    13L,
                    "You wake up in a dark dungeon. Do you search for a way out?",
                    List.of(
                        new QuestChoice(27L, "Search for an exit", 14L),
                        new QuestChoice(28L, "Stay and rest", 15L)),
                    "/images/dungeon.jpg"),
                new QuestStep(
                    14L,
                    "You find a locked door. Do you try to pick the lock?",
                    List.of(
                        new QuestChoice(29L, "Pick the lock", 16L),
                        new QuestChoice(30L, "Look for another way", 17L)),
                    "/images/dungeon.jpg"),
                new QuestStep(
                    15L,
                    "You hear footsteps approaching. Do you hide or confront the threat?",
                    List.of(
                        new QuestChoice(31L, "Hide", 18L),
                        new QuestChoice(32L, "Confront the threat", 19L)),
                    "/images/dungeon.jpg")),
            "/images/dungeon.jpg");
    questRepository.save(quest5);
  }

  public static void initializeUserTestData() {
    User user1 = new User(1L, "Khmelov", "Khmelov@example.com", "password123");
    userRepository.save(user1);

    User user2 = new User(2L, "Alexey", "Alexey@example.com", "password456");
    userRepository.save(user2);
  }
}
