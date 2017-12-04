package de.dhbw.softwareengineering.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @Column(name = "journalid")
    private int journalid;
    @Column(name = "journalname")
    private String journalName;
    @Column(name = "content")
    private String content;
    @Column(name = "username")
    private String username;
    @Column(name = "date")
    private long date;

    public int getJournalid() {
        return journalid;
    }

    public void setJournalid(int journalid) {
        this.journalid = journalid;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Journal{\n\t" +
                "ID=" + journalid +
                ",\n\tjournalname='" + journalName + '\'' +
                ",\n\tcontent=" + content +
                ",\n\tusername='" + username + '\'' +
                ",\n\tdate=" + date +
                "\n}";
    }
}
