package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;

import java.util.List;

public interface PasswordRecoveryRequestDAO {

    void addRequest(PasswordRecoveryRequest request);

    void deleteRequest(PasswordRecoveryRequest request);

    PasswordRecoveryRequest getRequestByUUID(String uuid);

    List<PasswordRecoveryRequest> getOldRequests();

    List<PasswordRecoveryRequest> getAllRequestsFromUser(String username);
}
