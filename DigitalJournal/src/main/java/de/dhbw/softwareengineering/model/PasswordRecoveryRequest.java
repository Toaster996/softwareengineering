package de.dhbw.softwareengineering.model;

import de.dhbw.softwareengineering.utilities.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "passwordRecovery")
public class PasswordRecoveryRequest {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "recoveryUUID")
    private String recoveryUUID;
    @Column(name = "creationDate")
    private long creationDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecoveryUUID() {
        return recoveryUUID;
    }

    public void setRecoveryUUID(String recoveryUUID) {
        this.recoveryUUID = recoveryUUID;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String toString() {
        return Constants.prettyPrinter.formatObject(this);
    }

}
