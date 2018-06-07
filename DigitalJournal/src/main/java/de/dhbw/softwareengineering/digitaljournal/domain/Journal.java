package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;

import static de.dhbw.softwareengineering.digitaljournal.util.Constants.JOURNAL_CONTENT_SIZE;

@Data
@Entity
public class Journal implements Serializable {
    @Id
    private String journalid;
    private String journalName;
    @Column(length = JOURNAL_CONTENT_SIZE)
    private String content;
    private String username;
    private long date;
    private boolean isShared;
    @OneToMany
    @JoinColumn(name = "journalid")
    private List<Image> images;

    public Journal(){}

    public Journal(String journalid, String journalName, String content, String username, long date) {
        this.journalid = journalid;
        this.journalName = journalName;
        this.content = content;
        this.username = username;
        this.date = date;
    }
}
