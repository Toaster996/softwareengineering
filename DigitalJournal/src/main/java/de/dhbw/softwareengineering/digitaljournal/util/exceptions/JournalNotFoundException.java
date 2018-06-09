package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class JournalNotFoundException extends RuntimeException{

    public JournalNotFoundException(String id){
        super("No journal found with id: " + id);
    }

}
