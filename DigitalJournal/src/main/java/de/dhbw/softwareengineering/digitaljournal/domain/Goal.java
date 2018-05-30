package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Goal implements Serializable {
    @Id
    private String id;
    private String username;
    private String name;
    private long date;
    private String description;
    private int daysLeft;
    private boolean checked;
    private boolean is_past; //TODO I'd like to remove this member, but this causes a fatal SQL-Error
    private boolean hasBeenNotified;
}
