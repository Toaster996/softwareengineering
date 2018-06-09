package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String identifier){
        super("No user found with identifier: " + identifier);
    }

}
