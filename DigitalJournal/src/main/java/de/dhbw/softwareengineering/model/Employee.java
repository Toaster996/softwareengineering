package de.dhbw.softwareengineering.model;

public class Employee {
    private String name;
    private long id;
    private String contractNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contractNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contractNumber = contactNumber;
    }
}
