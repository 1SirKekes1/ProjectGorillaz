package myasnikov.dao;


import myasnikov.entity.Quest;
import org.hibernate.SessionFactory;

public class QuestDao extends GenericDao<Quest> {
    public QuestDao(SessionFactory sessionFactory) {
        super(Quest.class, sessionFactory);
    }
}
