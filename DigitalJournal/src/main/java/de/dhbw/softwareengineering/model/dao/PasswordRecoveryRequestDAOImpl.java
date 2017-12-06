package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.utilities.Constants;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PasswordRecoveryRequestDAOImpl implements PasswordRecoveryRequestDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRequest(PasswordRecoveryRequest request) {
        HibernateUtil.create(request, sessionFactory);
    }

    public void deleteRequest(PasswordRecoveryRequest request) {
        HibernateUtil.delete(request, sessionFactory);
    }

    public PasswordRecoveryRequest getRequestByUUID(String uuid) {
        return (PasswordRecoveryRequest)HibernateUtil.getData(PasswordRecoveryRequest.class, "recoveryUUID",uuid,sessionFactory);
    }

    public List<PasswordRecoveryRequest> getOldRequests() {
        Session session = null;
        List<PasswordRecoveryRequest> requests = null;

        try {
            session = this.sessionFactory.openSession();
            requests = session.createCriteria(PasswordRecoveryRequest.class).add(Restrictions.lt("creationDate", System.currentTimeMillis() - Constants.ONE_HOUR_IN_MILLIS)).list();
        } catch (Exception e) {
            Constants.prettyPrinter.error(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return requests;
    }

    @Override
    public List<PasswordRecoveryRequest> getAllRequestsFromUser(String username) {
        Session session = null;
        List<PasswordRecoveryRequest> requests = null;

        try {
            session = this.sessionFactory.openSession();
            requests = session.createCriteria(PasswordRecoveryRequest.class).add(Restrictions.eq("username",username)).list();
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
