package de.dhbw.softwareengineering.model;

import de.dhbw.softwareengineering.utilities.Constants;

import javax.persistence.*;

@Entity
@Table(name = "contactRequest")
public class ContactRequest {

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    @Column(name = "contactRequest_id", unique = true, nullable = false)
    private int id;
    @Column(name = "contactRequest_name")
    private String name;
    @Column(name = "contactRequest_email")
    private String email;
    @Column(name = "contactRequest_message")
    private String message;
    @Column(name = "contactRequest_solved")
    private boolean solved;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String toString() {
        return Constants.prettyPrinter.formatObject(this);
    }
}
