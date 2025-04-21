package myasnikov.utility;

import myasnikov.config.HibernateConfig;
import myasnikov.entity.*;
import myasnikov.dao.QuestDao;
import myasnikov.dao.QuestStepDao;
import myasnikov.dao.QuestChoiceDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class TextToQuestParser {
    private static final Pattern QUEST_HEADER_PATTERN = Pattern.compile("^Quest:\\s*(.+)$");
    private static final Pattern QUEST_DESC_PATTERN = Pattern.compile("^\\*\\s*(.+)$");
    private static final Pattern STEP_HEADER_PATTERN = Pattern.compile("^Step:\\s*(\\w+)(?:\\s+\\((WIN|LOSE)\\))?$");
    private static final Pattern CHOICE_PATTERN = Pattern.compile("^-\\s*(.*?)\\s*->\\s*(\\S+)$");
    private static final Pattern IMAGE_PATTERN = Pattern.compile("^image:\\s*(.+)$");

    private final String questsDirectoryPath;
    private final String imagesBasePath;
    private final QuestDao questDao;
    private final QuestStepDao questStepDao;
    private final QuestChoiceDao questChoiceDao;

    public TextToQuestParser(String questsDirPath,
                             String imagesBasePath,
                             QuestDao questDao,
                             QuestStepDao questStepDao,
                             QuestChoiceDao questChoiceDao) {
        this.questsDirectoryPath = normalizePath(questsDirPath);
        this.imagesBasePath = normalizePath(imagesBasePath);
        this.questDao = questDao;
        this.questStepDao = questStepDao;
        this.questChoiceDao = questChoiceDao;
    }

    private String normalizePath(String path) {
        return path.startsWith("/") ? path.substring(1) : path;
    }

    public void parse() throws IOException {
        List<String> questFiles = getQuestFiles();
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        for (String questFile : questFiles) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();

                Quest quest = parseQuestFile(questFile);
                if (!questExists(session, quest)) {
                    saveQuest(session, quest);
                    System.out.println("Quest saved: " + quest.getName());
                } else {
                    System.out.println("Quest already exists, skipping: " + quest.getName());
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                throw new IOException("Failed to process quest file: " + questFile, e);
            }
        }
    }

    private boolean questExists(Session session, Quest quest) {
        try {
            return session.createQuery(
                            "SELECT COUNT(q) > 0 FROM Quest q WHERE q.name = :name",
                            Boolean.class)
                    .setParameter("name", quest.getName())
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        }
    }

    private void saveQuest(Session session, Quest quest) {
        for (QuestStep step : quest.getSteps()) {
            session.persist(step);

            if (step.getChoices() != null) {
                for (QuestChoice choice : step.getChoices()) {
                    choice.setStep(step);
                    session.persist(choice);
                }
            }
        }

        session.persist(quest);
    }

    private List<String> getQuestFiles() throws IOException {
        URL dirURL = getClass().getClassLoader().getResource(questsDirectoryPath);
        if (dirURL == null) {
            throw new IOException("Resource directory not found: " + questsDirectoryPath);
        }

        if (dirURL.getProtocol().equals("jar")) {
            return getFilesFromJar(dirURL);
        } else {
            return getFilesFromDirectory(dirURL);
        }
    }

    private List<String> getFilesFromJar(URL dirURL) throws IOException {
        String jarPath = dirURL.getPath().replaceFirst("^file:(//)?", "").split("!")[0];
        List<String> filenames = new ArrayList<>();

        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();

                if (!entry.isDirectory()
                        && entryName.startsWith(questsDirectoryPath + "/")
                        && entryName.endsWith(".txt")) {
                    filenames.add(entryName.substring(questsDirectoryPath.length() + 1));
                }
            }
        }

        if (filenames.isEmpty()) {
            throw new IOException("No quest files found in: " + questsDirectoryPath);
        }
        return filenames;
    }

    private List<String> getFilesFromDirectory(URL dirURL) throws IOException {
        try {
            Path dirPath = Paths.get(dirURL.toURI());
            return Files.list(dirPath)
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".txt"))
                    .map(file -> file.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            throw new IOException("Invalid resource path: " + questsDirectoryPath, e);
        }
    }

    private Quest parseQuestFile(String questFile) throws IOException {
        List<String> lines = readQuestFileLines(questFile);
        Quest quest = new Quest();
        Map<String, QuestStep> stepMap = new HashMap<>();
        QuestStep currentStep = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            Matcher questHeaderMatcher = QUEST_HEADER_PATTERN.matcher(line);
            Matcher questDescMatcher = QUEST_DESC_PATTERN.matcher(line);
            Matcher imageMatcher = IMAGE_PATTERN.matcher(line);
            Matcher stepHeaderMatcher = STEP_HEADER_PATTERN.matcher(line);

            if (questHeaderMatcher.find()) {
                quest.setName(questHeaderMatcher.group(1).trim());
            } else if (questDescMatcher.find() && currentStep == null) {
                quest.setDescription(questDescMatcher.group(1).trim());
            } else if (imageMatcher.find() && currentStep == null) {
                quest.setImageData(loadImage(imageMatcher.group(1)));
            } else if (stepHeaderMatcher.find()) {
                currentStep = createStep(stepHeaderMatcher, quest, stepMap);
            } else if (currentStep != null) {
                processStepContent(currentStep, line);
            }
        }

        validateQuest(quest);
        resolveStepReferences(stepMap);
        return quest;
    }

    private QuestStep createStep(Matcher stepHeaderMatcher, Quest quest, Map<String, QuestStep> stepMap) {
        String stepName = stepHeaderMatcher.group(1);
        EndType endType = stepHeaderMatcher.group(2) != null
                ? EndType.valueOf(stepHeaderMatcher.group(2))
                : EndType.NONE;

        QuestStep step = new QuestStep();
        step.setName(stepName);
        step.setEndType(endType);
        step.setChoices(new ArrayList<>());

        stepMap.put(stepName, step);
        if (quest.getSteps() == null) {
            quest.setSteps(new ArrayList<>());
        }
        quest.getSteps().add(step);
        return step;
    }

    private void processStepContent(QuestStep step, String line) throws IOException {
        Matcher imageMatcher = IMAGE_PATTERN.matcher(line);
        Matcher descMatcher = QUEST_DESC_PATTERN.matcher(line);
        Matcher choiceMatcher = CHOICE_PATTERN.matcher(line);

        if (imageMatcher.find()) {
            step.setImageData(loadImage(imageMatcher.group(1)));
        } else if (descMatcher.find()) {
            step.setDescription(descMatcher.group(1));
        } else if (choiceMatcher.find()) {
            QuestChoice choice = new QuestChoice();
            choice.setText(choiceMatcher.group(1));
            choice.setStep(step);
            choice.setNextStepReference(choiceMatcher.group(2));
            step.getChoices().add(choice);
        }
    }

    private byte[] loadImage(String imagePath) throws IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        String resourcePath = imagesBasePath + "/" + imagePath;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Image file not found in resources: " + resourcePath);
            }
            return is.readAllBytes();
        }
    }

    private void resolveStepReferences(Map<String, QuestStep> stepMap) {
        stepMap.values().forEach(step -> {
            if (step.getChoices() != null) {
                step.getChoices().forEach(choice -> {
                    String nextStepRef = choice.getNextStepReference();
                    if (nextStepRef != null && !stepMap.containsKey(nextStepRef)) {
                        throw new IllegalStateException("Invalid step reference: " + nextStepRef);
                    }
                    choice.setNextStep(stepMap.get(nextStepRef));
                    choice.setNextStepReference(null); // очищаем временное поле
                });
            }
        });
    }

    private void validateQuest(Quest quest) {
        if (quest.getName() == null || quest.getName().isEmpty()) {
            throw new IllegalStateException("Quest name is required");
        }

        if (quest.getDescription() == null || quest.getDescription().isEmpty()) {
            throw new IllegalStateException("Quest description is required");
        }

        if (quest.getSteps() == null || quest.getSteps().isEmpty()) {
            throw new IllegalStateException("Quest must have at least one step");
        }

        long winSteps = quest.getSteps().stream()
                .filter(step -> step.getEndType() == EndType.WIN)
                .count();
        if (winSteps == 0) {
            throw new IllegalStateException("Quest must have at least one WIN step");
        }
    }

    private List<String> readQuestFileLines(String questFile) throws IOException {
        String resourcePath = questsDirectoryPath + "/" + questFile;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().collect(Collectors.toList());
        }
    }
}