package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.Journal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        Session session = this.sessionFactory.openSession();
        List<Journal> journals = session.createQuery("from Journal").list();
        session.close();
        return journals;
    }
}
