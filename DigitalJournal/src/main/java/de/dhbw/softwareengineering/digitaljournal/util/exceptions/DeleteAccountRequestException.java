package de.dhbw.softwareengineering.digitaljournal.util.exceptions;

public class DeleteAccountRequestException extends Exception {

    public DeleteAccountRequestException(String uuid) {
        super("No delete request found with ID: " + uuid);
    }

}
