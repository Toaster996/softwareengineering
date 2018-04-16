package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an entry of a PasswordRecoveryRequest from the database.
 */
@Data
@Entity
public class PasswordRecoveryRequest {

    @Id
    private String recoveryUUID;
    private String username;
    private long creationDate;

}