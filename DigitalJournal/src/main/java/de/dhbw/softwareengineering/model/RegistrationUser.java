package de.dhbw.softwareengineering.model;

public class RegistrationUser {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegistrationUser{\n\t" +
                "name='" + name + '\'' +
                ",\n\temail='" + email + '\'' +
                ",\n\tpassword='" + password + '\'' +
                "\n}";
    }
}
