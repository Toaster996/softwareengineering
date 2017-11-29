package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO{

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }

    @Override
    public List<User> list() {
        Session session = this.sessionFactory.openSession();
        List<User> personList = session.createQuery("from User").list();
        session.close();
        return personList;
    }

}
