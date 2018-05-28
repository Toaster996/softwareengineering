package de.dhbw.softwareengineering.digitaljournal.domain.form;

import lombok.Data;

/**
 * This class is used to store the entered data the user inputs during the login process.
 */
@Data
public class LoginUser {
    private String loginName;
    private String loginPassword;
}
