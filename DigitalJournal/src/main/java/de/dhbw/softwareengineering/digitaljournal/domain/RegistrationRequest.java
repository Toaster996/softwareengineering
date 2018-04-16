package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an entry of a registration from the database.
 */
@Data
@Entity
public class RegistrationRequest {

    @Id
    private String username;
    private String registrationUUID;
    private long date;

}
