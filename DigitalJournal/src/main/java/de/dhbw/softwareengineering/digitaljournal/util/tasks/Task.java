package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;

public abstract class Task {

    AbstractService[] services;

    Task(AbstractService... service) {
        this.services = service;
    }

    public abstract void execute();
}
