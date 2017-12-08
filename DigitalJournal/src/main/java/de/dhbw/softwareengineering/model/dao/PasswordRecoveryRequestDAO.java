package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.PasswordRecoveryRequest;
import de.dhbw.softwareengineering.model.RegistrationRequest;

import java.util.List;

/**
 * The password recovery requests Data-Access-Object interface. It provides all the methods needed to interact with the database.
 *
 * @author straub-florian
 */
public interface PasswordRecoveryRequestDAO {

    /**
     * Adds a given request to the database
     *
     * @param request the request that gets added
     */
    void addRequest(PasswordRecoveryRequest request);

    /**
     * Removes the given request from the database
     *
     * @param request the request that gets removed
     */
    void deleteRequest(PasswordRecoveryRequest request);

    /**
     * Tries to find an {@link PasswordRecoveryRequest} linked to the given uuid
     *
     * @param uuid the uuid to check
     * @return the corresponding {@link RegistrationRequest}
     */
    PasswordRecoveryRequest getRequestByUUID(String uuid);

    /**
     * Finds all requests older than one hour
     *
     * @return a list of old recovery requests
     */
    List<PasswordRecoveryRequest> getOldRequests();

    /**
     * Finds all requests of a given user
     *
     * @param username the users name
     * @return a list of old recovery requests
     */
    List<PasswordRecoveryRequest> getAllRequestsFromUser(String username);
}
