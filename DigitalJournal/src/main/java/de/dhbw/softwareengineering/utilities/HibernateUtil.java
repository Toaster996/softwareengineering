package de.dhbw.softwareengineering.utilities;

import de.dhbw.softwareengineering.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Entity;

/**
 * @author straub-florian
 * <p>
 * Provides basic hibernate functionality
 */
public class HibernateUtil {

    /**
     * Creates a new entry in the corresponding table of the given entity
     *
     * @param databaseEntity the database entity
     * @param sessionFactory the session factory
     */
    public static void create(Object databaseEntity, SessionFactory sessionFactory) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(databaseEntity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        }
    }

    /**
     * Updates an existing entry in the corresponding table of the given entity
     *
     * @param databaseEntity the database entity
     * @param sessionFactory the session factory
     */
    public static void update(Object databaseEntity, SessionFactory sessionFactory) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.update(databaseEntity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        }
    }

    /**
     * Deletes an existing entry in the corresponding table of the given entity
     *
     * @param databaseEntity the database entity
     * @param sessionFactory the session factory
     */
    public static void delete(Object databaseEntity, SessionFactory sessionFactory) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.delete(databaseEntity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        }
    }

    /**
     * Gets an DAO from the database through the given parameters
     * @param clazz The DAO Class
     * @param identifier basically the column
     * @param sessionFactory the session factory
     * @return a DAO object
     */
    public static Object getDAOByIdentifier(Class<?> clazz, String identifier, SessionFactory sessionFactory) {
        Session session = null;
        Object o = null;
        try {
            session = sessionFactory.openSession();
            o = session.get(clazz, identifier);
            session.close();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return o;
    }

}
