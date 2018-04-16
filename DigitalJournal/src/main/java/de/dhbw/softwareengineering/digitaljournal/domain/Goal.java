package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Goal {
    @Id
    private String id;
    private String username;
    private String name;
    private long date;
    private String description;
    private int daysLeft;
}
