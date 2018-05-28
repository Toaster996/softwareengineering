package de.dhbw.softwareengineering.digitaljournal.domain.form;

import lombok.Data;

/**
 * This class is used to store the entered data the user inputs during the registration process.
 */
@Data
public class RegistrationUser {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
