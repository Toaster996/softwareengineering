package de.dhbw.softwareengineering.model;

import javax.persistence.*;

@Entity
@Table(name = "registration_request")
public class RegistrationRequest {

    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "registration_uuid")
    private String registration_uuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegistration_uuid() {
        return registration_uuid;
    }

    public void setRegistration_uuid(String registration_uuid) {
        this.registration_uuid = registration_uuid;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{\n\t" +
                "username='" + username + '\'' +
                ",\n\tregistration_uuid='" + registration_uuid + '\'' +
                "\n}";
    }

}
