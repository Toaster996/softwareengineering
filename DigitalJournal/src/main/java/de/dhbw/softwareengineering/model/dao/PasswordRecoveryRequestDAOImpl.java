package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
        return (PasswordRecoveryRequest)HibernateUtil.getDAOByIdentifier(PasswordRecoveryRequest.class,uuid,sessionFactory);
    }
}
