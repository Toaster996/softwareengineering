package de.dhbw.softwareengineering.utilities;

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

}
