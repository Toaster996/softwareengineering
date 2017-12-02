package de.dhbw.softwareengineering.model.dao;

import de.dhbw.softwareengineering.model.Journal;

import java.util.List;

public interface JournalDAO {
    public void newJournal(Journal journal);
    public void updateJournal(Journal journal);
    public List<Journal> getallJournals(String user);
}
