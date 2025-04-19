package myasnikov.service;

import myasnikov.config.HibernateConfig;
import myasnikov.dao.QuestDao;
import myasnikov.entity.Quest;


import java.util.List;
import java.util.Optional;

public class QuestService implements Service<Quest> {
    private final QuestDao questDAO = new QuestDao(HibernateConfig.getSessionFactory());

    @Override
    public Optional<Quest> findById(Long id) {
        return Optional.ofNullable(questDAO.getById(id));
    }

    @Override
    public List<Quest> findAll() {
        return questDAO.findAll();
    }

    @Override
    public void deleteById(Long id) {
        questDAO.deleteById(id);
    }

    @Override
    public void save(Quest quest) {
        questDAO.save(quest);
    }
}
