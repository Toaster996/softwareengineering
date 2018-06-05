package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SharedJournal {
    //TODO ID
    @Id
    String journalName;
    String coAuthor;
}
