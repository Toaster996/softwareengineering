package de.dhbw.softwareengineering.model;

public class LoginUser {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return String.format( "LoginUser {\n\tname = %s \n\tpassword = %s\n}", name, password);
    }
}
