package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.RegistrationRequest;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        HibernateUtil.create(request, sessionFactory);
    }

    public void removeRequest(String uuid) { HibernateUtil.delete(getRequestByUUID(uuid), sessionFactory); }

    public RegistrationRequest getRequestByUUID(String uuid) {
        Session session = null;
        RegistrationRequest request = null;
        try {
            // open the session
            session = this.sessionFactory.openSession();
            // select table
            Criteria criteria = session.createCriteria(RegistrationRequest.class);
            // set restriction to only get an uuid that matches the given parameter
            request = (RegistrationRequest) criteria.add(Restrictions.eq("registration_uuid",uuid)).uniqueResult();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            // close the session if it can be closed
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
            Constants.prettyPrinter.error(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return requests;
    }
}
