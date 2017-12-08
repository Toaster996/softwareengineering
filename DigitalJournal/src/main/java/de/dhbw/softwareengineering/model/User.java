package de.dhbw.softwareengineering.model;

import de.dhbw.softwareengineering.utilities.Constants;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
/**
 * Represents the an entry of a user from the database.
 */
public class User {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "registrationDate")
    private long registrationDate;
    @Column(name = "password")
    private String password;
    @Column(name = "verified")
    private boolean verified;

    @OneToOne
    @JoinColumn(name="username")
    private PasswordRecoveryRequest passwordRecoveryRequest;

    @OneToOne
    @JoinColumn(name="username")
    private RegistrationRequest registrationRequest;

    @OneToMany
    @JoinColumn(name = "username")
    private Set<Journal> journals;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String toString() {
        return Constants.prettyPrinter.formatObject(this);
    }
}
