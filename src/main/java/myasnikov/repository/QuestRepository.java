package myasnikov.repository;

import myasnikov.entity.Quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static myasnikov.config.AppConfig.initializeQuestTestData;

public class QuestRepository implements Repository<Quest> {
  private final Map<Long, Quest> quests = new HashMap<>();
  private long nextId = 1;

  public QuestRepository() {
    initializeQuestTestData();
  }

  @Override
  public void save(Quest quest) {
    if (quest.getId() == null) {
      quest.setId(nextId++);
    }
    quests.put(quest.getId(), quest);
  }

  @Override
  public Optional<Quest> findById(Long id) {
    return Optional.ofNullable(quests.get(id));
  }

  @Override
  public Map<Long, Quest> findAll() {
    return new HashMap<>(quests);
  }

  @Override
  public void deleteById(Long id) {
    quests.remove(id);
  }
}
