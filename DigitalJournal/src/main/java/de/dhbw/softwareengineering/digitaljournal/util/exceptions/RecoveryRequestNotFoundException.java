package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class RecoveryRequestNotFoundException extends RuntimeException{

    public RecoveryRequestNotFoundException(String id){
        super("No recovery request found with id: " + id);
    }

}
