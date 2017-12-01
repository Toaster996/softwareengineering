package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * The users Data-Access-Object implementation. It contains all the logic needed to interact with the database.
 *
 * @author straub-florian
 */
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;

    /**
     * Used by spring to set the session factory
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createNewUser(User user) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }

    public void updateUser(User user) {
        if (getUserByName(user.getUsername()) != null) {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            session.close();
        } else {
            System.err.println("[UserDAOImpl] Could not find corresponding user!");
        }
    }

    public List<User> getAllUsers() {
        Session session = this.sessionFactory.openSession();
        List<User> users = session.createQuery("from User").list();
        session.close();
        return users;
    }

    public User getUserByName(String username) {
        Session session = null;
        User user = null;
        try {
            session = this.sessionFactory.openSession();
            user = (User) session.get(User.class, username);
            session.close();
        } catch (Exception e) {
            System.out.println("[UserDAOImpl] Error while getting user by name(\'" + username + "\'): " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public User getUserByEMail(String email) {
        Session session = null;
        User user = null;
        try {
            session = this.sessionFactory.openSession();
            user = (User) session.get(User.class, email);
            session.close();
        } catch (Exception e) {
            System.out.println("[UserDAOImpl] Error while getting user by email(\'" + email + "\'): " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void removeUser(String username) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(getUserByName(username));
        tx.commit();
        session.close();
    }
}
