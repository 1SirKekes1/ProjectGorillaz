package myasnikov.dao;

import myasnikov.entity.QuestChoice;
import org.hibernate.SessionFactory;


public class QuestChoiceDao extends GenericDao<QuestChoice> {
    public QuestChoiceDao(SessionFactory sessionFactory) {
        super(QuestChoice.class, sessionFactory);
    }
}
