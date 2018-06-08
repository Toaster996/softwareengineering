package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an entry of a registration from the database.
 */
@Data
@Entity
public class DeleteAccountRequest {

    @Id
    private String requestid;
    private String username;
    private long date;

}
