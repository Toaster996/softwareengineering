package de.dhbw.softwareengineering.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
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
    private RegistrationRequest registrationRequest;


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

    @Override
    public String toString() {
        return "User {\n\t" +
                "username='" + username + '\'' +
                ",\n\temail='" + email + '\'' +
                ",\n\tregistrationDate=" + registrationDate +
                ",\n\tpassword='" + password + '\'' +
                "\n}";
    }
}
