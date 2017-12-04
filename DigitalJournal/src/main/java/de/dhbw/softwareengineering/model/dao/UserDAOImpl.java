package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        HibernateUtil.create(user, sessionFactory);
    }

    public void updateUser(User user) {
        HibernateUtil.update(user, sessionFactory);
    }

    @Override
    public void removeUser(String username) {
        HibernateUtil.delete(getUserByName(username), sessionFactory);
    }

    public List<User> getAllUsers() {
        Session session = this.sessionFactory.openSession();
        List<User> users = session.createQuery("from User").list();
        session.close();
        return users;
    }

    public User getUserByName(String username) {
        return getUserByIdentifier(username);
    }

    @Override
    public User getUserByEMail(String email) {
        return getUserByIdentifier(email);
    }

    private User getUserByIdentifier(String identifier) {
        Session session = null;
        User user = null;
        try {
            session = this.sessionFactory.openSession();
            user = (User) session.get(User.class, identifier);
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }
}
