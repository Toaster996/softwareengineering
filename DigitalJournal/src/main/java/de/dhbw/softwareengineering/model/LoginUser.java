package de.dhbw.softwareengineering.model;

public class LoginUser {
    private String loginName;
    private String loginPassword;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String toString(){
        return String.format( "LoginUser {\n\tname = %s \n\tpassword = %s\n}", loginName, loginPassword);
    }
}
