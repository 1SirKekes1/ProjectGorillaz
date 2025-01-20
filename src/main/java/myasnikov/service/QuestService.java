package myasnikov.service;

import myasnikov.config.AppConfig;
import myasnikov.entity.Quest;
import myasnikov.repository.Repository;

import java.util.Map;
import java.util.Optional;

public class QuestService implements Service<Quest> {
  private final Repository<Quest> questRepository = AppConfig.getQuestRepository();

  @Override
  public void save(Quest quest) {
    if (quest.getTitle() == null || quest.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Quest title cannot be empty");
    }
    questRepository.save(quest);
  }

  @Override
  public Optional<Quest> findById(Long id) {
    return questRepository.findById(id);
  }

  @Override
  public Map<Long, Quest> findAll() {
    return questRepository.findAll();
  }

  @Override
  public void deleteById(Long id) {
    questRepository.deleteById(id);
  }
}
