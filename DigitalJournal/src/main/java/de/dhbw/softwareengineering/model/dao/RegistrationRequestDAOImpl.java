package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.RegistrationRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * The registration request's Data-Access-Object implementation. It contains all the logic needed to interact with the database.
 *
 * @author straub-florian
 */
public class RegistrationRequestDAOImpl implements RegistrationRequestDAO {

    private SessionFactory sessionFactory;

    /**
     * Used by spring to set the session factory
     *
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRequest(RegistrationRequest request) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(request);
        tx.commit();
        session.close();
    }

    public RegistrationRequest getRequestByUUID(String uuid) {
        Session session = null;
        RegistrationRequest request = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(RegistrationRequest.class);
            request = (RegistrationRequest) criteria.add(Restrictions.eq("registration_uuid",uuid)).uniqueResult();
        } catch (Exception e) {
            System.out.println("[RegistrationRequestDAOImpl] Error while getting request by UUID(\'" + uuid + "\'): " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return request;
    }

    public List<RegistrationRequest> getOldRequests(long olderThan){
        Session session = null;
        List<Object> rawResult = null;
        List<RegistrationRequest> requests = null;
        try {
            session = this.sessionFactory.openSession();
            String olderThanF = String.valueOf(olderThan).toLowerCase().replace("l","");
            rawResult = session.createQuery("FROM User AS u LEFT JOIN u.registrationRequest r WHERE u.username = r.username AND u.registrationDate < " + olderThanF).list();

            if(rawResult.size() > 0){
                requests = new ArrayList<>();
                for(Object o : rawResult){
                    requests.add((RegistrationRequest) ((Object[]) o)[1]);
                }
            }

        } catch (Exception e) {
            System.out.println("[RegistrationRequestDAOImpl] Error while deleting old requests: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return requests;
    }

    public void removeRequest(String uuid) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(getRequestByUUID(uuid));
        tx.commit();
        session.close();
    }
}
