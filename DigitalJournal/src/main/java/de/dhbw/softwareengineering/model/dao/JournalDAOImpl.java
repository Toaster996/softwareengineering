package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.Journal;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class JournalDAOImpl implements JournalDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void newJournal(Journal journal) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(journal);
        tx.commit();
        session.close();
    }

    @Override
    public void updateJournal(Journal journal) {

    }

    @Override
    public List<Journal> getallJournals(String user) {
        List<Journal> journals = new ArrayList<>();

        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("FROM User AS u INNER JOIN u.journals j WHERE u.username = j.username AND u.username LIKE :user ORDER BY j.date DESC");
              q.setParameter("user", user);
        List<Object> rawResult = q.list();
        System.out.println("Raw-list");
        if(rawResult.size() > 0){
            for(Object o : rawResult) {
                Object rawJournal = ((Object[]) o)[1];
                if (rawJournal instanceof Journal) {
                    Journal j = (Journal) rawJournal;
                    System.out.println(j);
                    journals.add(j);
                }
            }
        }

        session.close();
        return journals;
    }
}
