package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Represents an entry of an user from the database.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String email;
    private long registrationDate;
    private String password;
    private boolean verified;

    @OneToOne
    @JoinColumn(name = "username")
    private PasswordRecoveryRequest passwordRecoveryRequest;

    @OneToOne
    @JoinColumn(name = "username")
    private RegistrationRequest registrationRequest;

    @OneToMany
    @JoinColumn(name = "username")
    private List<Journal> journals;
}

