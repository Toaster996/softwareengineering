package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.ContactRequest;
import de.dhbw.softwareengineering.model.Journal;

import java.util.List;

public interface ContactRequestDAO {

    void addRequest(ContactRequest request);

    void solveRequest(ContactRequest request);

    List<ContactRequest> getUnsentRequests();

    void deleteRequest(ContactRequest request);
}
