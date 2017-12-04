package de.dhbw.softwareengineering.model.dao;

import java.util.List;

import de.dhbw.softwareengineering.model.User;

/**
 * The users Data-Access-Object interface. It provides all the methods needed to interact with the database.
 *
 * @author straub-florian
 */
public interface UserDAO {

    /**
     * Creates a new User in the database
     *
     * @param user the user that gets created
     */
    void createNewUser(User user);

    /**
     * Updates an existing user
     *
     * @param user the user that gets updated
     */
    void updateUser(User user);

    /**
     * Tries to remove a user by the given username
     *
     * @param username the name of the user
     */
    void removeUser(String username);

    /**
     * @return A list of all users
     */
    List<User> getAllUsers();

    /**
     * Tries to find a User with the given name and returns it.
     *
     * @param username the name of the user
     * @return the actual user
     */
    User getUserByName(String username);

    /**
     * Tries to find a User with the given email and returns it.
     *
     * @param email the email of the user
     * @return the actual user
     */
    User getUserByEMail(String email);

}