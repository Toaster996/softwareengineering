package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String identifier) {
        super("No user found with identifier: " + identifier);
    }

}
