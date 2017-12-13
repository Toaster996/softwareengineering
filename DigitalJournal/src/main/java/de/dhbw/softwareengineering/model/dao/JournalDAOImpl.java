package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.Journal;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class JournalDAOImpl implements JournalDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void newJournal(Journal journal) {
        HibernateUtil.create(journal, sessionFactory);
    }

    @Override
    public void updateJournal(Journal journal) {
        HibernateUtil.update(journal, sessionFactory);
    }

    @Override
    public void removeJournal(Journal journal) { HibernateUtil.delete(journal, sessionFactory); }

    @Override
    public List<Journal> getAllJournals(String user) {
        List<Journal> journals = new ArrayList<>();

        Session session = this.sessionFactory.openSession();
        Query q = session.createQuery("FROM User AS u INNER JOIN u.journals j WHERE u.username = j.username AND u.username LIKE :user ORDER BY j.date DESC");
        q.setParameter("user", user);
        List<Object> rawResult = q.list();

        if (rawResult.size() > 0) {
            for (Object o : rawResult) {
                Object rawJournal = ((Object[]) o)[1];
                if (rawJournal instanceof Journal) {
                    Journal j = (Journal) rawJournal;
                    journals.add(j);
                }
            }
        }

        session.close();
        return journals;
    }

    @Override
    public Journal getJournal(int id) {
        Session session = null;
        Journal j = null;
        try {
            session = sessionFactory.openSession();
            j = (Journal) session.get(Journal.class, id);
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return j;

    }
}
