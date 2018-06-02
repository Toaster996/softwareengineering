package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Friend {
    @Id
    String id;
    String name;
    String friendName;
    String userID;
    String relationship;
    int sharedEvents;
}
