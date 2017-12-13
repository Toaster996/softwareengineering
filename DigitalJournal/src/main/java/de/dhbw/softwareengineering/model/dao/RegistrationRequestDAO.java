package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.RegistrationRequest;

import java.util.List;

/**
 * The registration requests Data-Access-Object interface. It provides all the methods needed to interact with the database.
 *
 * @author straub-florian
 */
public interface RegistrationRequestDAO {

    /**
     * Adds a given request to the database
     *
     * @param request the request that gets added
     */
    public void addRequest(RegistrationRequest request);

    /**
     * Removes the given request from the database
     *
     * @param uuid the request's uuid
     */
    public void removeRequest(String uuid);

    /**
     * Tries to find an unverified user linked to the given uuid
     *
     * @param uuid the uuid to check
     * @return the corresponding {@link RegistrationRequest}
     */
    public RegistrationRequest getRequestByUUID(String uuid);

    /**
     * Finds all old requests older than the given parameter
     *
     * @param olderThan the maximum "birthdate" of the request
     * @return a list of old registration requests
     */
    public List<RegistrationRequest> getOldRequests(long olderThan);
}
