package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an entry of a contact request from the database.
 */
@Data
@Entity
public class ContactRequest {

    @Id
    private String id;
    private String name;
    private String email;
    private String message;
    private boolean solved;

}
