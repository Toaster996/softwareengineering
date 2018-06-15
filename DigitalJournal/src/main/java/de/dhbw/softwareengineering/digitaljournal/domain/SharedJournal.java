package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SharedJournal {
    //TODO ID
    @Id
    String id;
    String journalName;
    String coAuthor;

    public SharedJournal(){}

    public SharedJournal(String journalName, String coAuthor) {
        this.journalName = journalName;
        this.coAuthor = coAuthor;
    }
}
