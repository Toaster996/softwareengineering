package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class JournalNotFoundException extends Exception {

    public JournalNotFoundException(String id) {
        super("No journal found with id: " + id);
    }

}
