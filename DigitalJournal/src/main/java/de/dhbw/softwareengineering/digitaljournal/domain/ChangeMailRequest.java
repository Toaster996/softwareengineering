package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ChangeMailRequest {

    @Id
    private String username;
    private String oldmailid;
    private boolean oldconfirmed;
    private String newmail;
    private String newmailid;
    private boolean newconfirmed;
    private long date;

}
