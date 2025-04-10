package myasnikov.service;

import myasnikov.config.HibernateConfig;
import myasnikov.dao.QuestDao;
import myasnikov.entity.Quest;


import java.util.Map;
import java.util.Optional;

public class QuestService implements Service<Quest> {
  private final QuestDao questDAO = new QuestDao(HibernateConfig.getSessionFactory());

  @Override
  public void save(Quest quest) {
    if (quest.getTitle() == null || quest.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Quest title cannot be empty");
    }
    questDAO.save(quest);
  }

  @Override
  public Optional<Quest> findById(Long id) {
    return Optional.ofNullable(questDAO.getById(id));
  }

  @Override
  public Map<Long, Quest> findAll() {
    return questDAO.findAll();
  }

  @Override
  public void deleteById(Long id) {
    questDAO.deleteById(id);
  }
}
