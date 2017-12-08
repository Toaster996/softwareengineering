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
        Session session = null;
        List<User> users = null;

        try {
            // open the session
            session = this.sessionFactory.openSession();
            // select table
            users = session.createQuery("from User").list();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            // close the session if it can be closed
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return users;
    }

    public User getUserByName(String username) {
        return (User) HibernateUtil.getData(User.class, "username", username, sessionFactory);
    }

    @Override
    public User getUserByEMail(String email) {
        return (User) HibernateUtil.getData(User.class, "email", email, sessionFactory);
    }
}
