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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contactNumber) {
        this.contractNumber = contactNumber;
    }
}
