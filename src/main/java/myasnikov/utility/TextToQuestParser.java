package myasnikov.utility;

import myasnikov.entity.*;
import myasnikov.dao.QuestDao;
import myasnikov.dao.QuestStepDao;
import myasnikov.dao.QuestChoiceDao;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class TextToQuestParser {
    private final Path questsDirectoryPath;
    private final Path imagesBasePath;
    private final QuestDao questDao;
    private final QuestStepDao questStepDao;
    private final QuestChoiceDao questChoiceDao;
    private final Map<String, QuestStep> stepNameMap;

    public TextToQuestParser(String questsDirPath,
                             String imagesBasePath,
                             QuestDao questDao,
                             QuestStepDao questStepDao,
                             QuestChoiceDao questChoiceDao) {
        this.questsDirectoryPath = Paths.get(questsDirPath).toAbsolutePath();
        this.imagesBasePath = Paths.get(imagesBasePath).toAbsolutePath();
        this.questDao = questDao;
        this.questStepDao = questStepDao;
        this.questChoiceDao = questChoiceDao;
        this.stepNameMap = new HashMap<>();
        validatePaths();
    }

    private void validatePaths() {
        if (!Files.exists(questsDirectoryPath)) {
            throw new IllegalArgumentException("Quests directory path does not exist: " + questsDirectoryPath);
        }
        if (!Files.isDirectory(questsDirectoryPath)) {
            throw new IllegalArgumentException("Quests path is not a directory: " + questsDirectoryPath);
        }
        if (!Files.exists(imagesBasePath)) {
            throw new IllegalArgumentException("Images directory path does not exist: " + imagesBasePath);
        }
        if (!Files.isDirectory(imagesBasePath)) {
            throw new IllegalArgumentException("Images path is not a directory: " + imagesBasePath);
        }
    }

    public void parse() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(questsDirectoryPath, "*.txt")) {
            for (Path questFile : stream) {
                try {
                    Quest quest = parseQuestFile(questFile);
                    saveQuestWithDependencies(quest);
                } catch (Exception e) {
                    throw new IOException("Failed to process quest file: " + questFile.toString(), e);
                }
            }
        } catch (IOException e) {
            throw new IOException("Failed to read quests directory: " + questsDirectoryPath, e);
        }
    }

    private Quest parseQuestFile(Path questFile) throws IOException {
        List<String> lines;
        try {
            lines = Files.readAllLines(questFile);
        } catch (IOException e) {
            throw new IOException("Failed to read quest file: " + questFile, e);
        }

        Quest currentQuest = null;
        QuestStep currentStep = null;
        stepNameMap.clear();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//")) {
                continue;
            }

            if (line.startsWith("## ")) {
                currentQuest = new Quest();
                currentQuest.setTitle(line.substring(3).trim());
                currentQuest.setDescription("");
                currentQuest.setSteps(new ArrayList<>());
            } else if (line.startsWith("> ")) {
                if (currentQuest != null) {
                    currentQuest.setDescription(line.substring(2).trim());
                }
            } else if (line.startsWith("cover: ")) {
                if (currentQuest != null) {
                    String imagePath = line.substring(7).trim();
                    try {
                        currentQuest.setImageData(loadImage(imagePath));
                    } catch (IOException e) {
                        throw new IOException("Failed to load cover image for quest: " + imagePath, e);
                    }
                }
            } else if (line.startsWith("=== ")) {
                if (currentQuest == null) {
                    throw new IllegalStateException("Step defined before quest header in file: " + questFile.toString());
                }
                currentStep = parseStepHeader(line, currentQuest);
            } else if (line.startsWith("* ")) {
                if (currentStep != null) {
                    currentStep.setDescription(line.substring(2).trim());
                }
            } else if (line.startsWith("image: ")) {
                if (currentStep != null) {
                    String imagePath = line.substring(7).trim();
                    try {
                        currentStep.setImageData(loadImage(imagePath));
                    } catch (IOException e) {
                        throw new IOException("Failed to load step image: " + imagePath, e);
                    }
                }
            } else if (line.startsWith("- ")) {
                if (currentStep != null) {
                    QuestChoice choice = parseChoice(line);
                    currentStep.getChoices().add(choice);
                }
            }
        }

        if (currentQuest == null) {
            throw new IOException("No valid quest found in file: " + questFile.toString());
        }

        validateQuest(currentQuest);
        return currentQuest;
    }

    private QuestStep parseStepHeader(String line, Quest quest) {
        String stepDef = line.substring(4).trim();
        String[] parts = stepDef.split("\\s+", 2);
        String stepName = parts[0];
        EndType endType = parseEndType(stepDef);

        QuestStep step = new QuestStep();
        step.setDescription("");
        step.setEndType(endType != null ? endType : EndType.NONE);
        step.setChoices(new ArrayList<>());

        stepNameMap.put(stepName, step);
        quest.getSteps().add(step);
        return step;
    }

    private QuestChoice parseChoice(String line) {
        Pattern pattern = Pattern.compile("-\\s*(.*?)\\s*->\\s*(\\S+)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            QuestChoice choice = new QuestChoice();
            choice.setText(matcher.group(1));

            String targetStepName = matcher.group(2);
            QuestStep targetStep = stepNameMap.get(targetStepName);
            if (targetStep == null) {
                throw new IllegalArgumentException("Unknown step reference: " + targetStepName);
            }
            choice.setNextStep(targetStep);
            return choice;
        }
        throw new IllegalArgumentException("Invalid choice format: " + line);
    }

    private byte[] loadImage(String imagePath) throws IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        Path fullPath = imagesBasePath.resolve(imagePath.substring(1));
        if (!Files.exists(fullPath)) {
            throw new IOException("Image file not found: " + fullPath.toString());
        }
        return Files.readAllBytes(fullPath);
    }

    private EndType parseEndType(String stepDef) {
        if (stepDef.contains("(WIN)")) {
            return EndType.WIN;
        }
        if (stepDef.contains("(LOSE)")) {
            return EndType.LOSE;
        }
        return null;
    }

    private void validateQuest(Quest quest) {
        if (quest.getTitle() == null || quest.getTitle().isEmpty()) {
            throw new IllegalStateException("Quest title is required");
        }

        if (quest.getSteps() == null || quest.getSteps().isEmpty()) {
            throw new IllegalStateException("Quest must have at least one step");
        }

        for (QuestStep step : quest.getSteps()) {
            if (step.getDescription() == null) {
                throw new IllegalStateException("Step description cannot be null");
            }

            if (step.getChoices() == null) {
                throw new IllegalStateException("Step choices list cannot be null");
            }

            for (QuestChoice choice : step.getChoices()) {
                if (choice.getText() == null || choice.getText().isEmpty()) {
                    throw new IllegalStateException("Choice text cannot be empty");
                }

                if (choice.getNextStep() == null) {
                    throw new IllegalStateException("Choice must reference a next step");
                }

                if (!stepExistsInQuest(choice.getNextStep(), quest)) {
                    throw new IllegalStateException("Referenced step does not belong to this quest");
                }
            }
        }
    }

    private boolean stepExistsInQuest(QuestStep step, Quest quest) {
        return quest.getSteps().contains(step);
    }

    private void saveQuestWithDependencies(Quest quest) {
        questDao.save(quest);

        for (QuestStep step : quest.getSteps()) {
            questStepDao.save(step);

            for (QuestChoice choice : step.getChoices()) {
                questChoiceDao.save(choice);
            }
        }
    }
}