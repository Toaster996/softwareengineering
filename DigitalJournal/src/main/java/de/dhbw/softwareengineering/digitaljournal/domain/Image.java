package de.dhbw.softwareengineering.digitaljournal.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Image {
    @Id
    private String id;
    private String journalid;
    @Column(name = "image", nullable = false, length = 2 * 1024 * 1024)
    private byte[] image;
}
