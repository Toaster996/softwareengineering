package de.dhbw.softwareengineering.model;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @Column(name = "journalid")
    private int id;
    @Column(name = "journalname")
    private String name;
    @ForeignKey(name = "ownername")
    private User user;
    @Column(name = "content")
    private String content;
    @Column(name = "date")
    private Date date;

    @OneToOne
    @JoinColumn(name = "journal")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "name='" + name + '\'' +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
