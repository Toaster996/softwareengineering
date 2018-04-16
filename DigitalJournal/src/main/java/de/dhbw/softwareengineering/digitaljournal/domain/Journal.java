package de.dhbw.softwareengineering.digitaljournal.domain;

import de.dhbw.softwareengineering.digitaljournal.util.Constants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.JOURNAL_CONTENT_SIZE;

@Data
@Entity
public class Journal {
    @Id
    private String journalid;
    private String journalName;
    @Column(length = JOURNAL_CONTENT_SIZE)
    private String content;
    private String username;
    private long date;
}
