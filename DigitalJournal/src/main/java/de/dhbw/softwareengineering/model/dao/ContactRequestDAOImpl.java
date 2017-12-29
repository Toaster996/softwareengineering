package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.User;
import de.dhbw.softwareengineering.utilities.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ContactRequestDAOImpl implements ContactRequestDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRequest(ContactRequest request) {
        HibernateUtil.create(request, sessionFactory);
    }

    public void solveRequest(ContactRequest request) {
        request.setSolved(true);
        HibernateUtil.update(request, sessionFactory);
    }

    public List<ContactRequest> getUnsentRequests() {
        Session session = this.sessionFactory.openSession();
        List<ContactRequest> requests = session.createQuery("from ContactRequest as c WHERE c.solved = false").list();
        session.close();
        return requests;
    }

    public void deleteRequest(ContactRequest request) {
        HibernateUtil.delete(request, sessionFactory);
    }
}
