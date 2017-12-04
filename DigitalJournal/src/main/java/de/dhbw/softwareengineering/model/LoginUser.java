package de.dhbw.softwareengineering.model;

import de.dhbw.softwareengineering.utilities.Constants;

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
        return Constants.prettyPrinter.formatObject(this);
    }
}
