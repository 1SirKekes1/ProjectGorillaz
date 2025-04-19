package myasnikov.dao;


import myasnikov.entity.QuestStep;
import org.hibernate.SessionFactory;

public class QuestStepDao extends GenericDao<QuestStep> {
    public QuestStepDao(SessionFactory sessionFactory) {
        super(QuestStep.class, sessionFactory);
    }
}
