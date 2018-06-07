package de.dhbw.softwareengineering.digitaljournal.util.tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;

public abstract class Task {

    protected AbstractService[] services;

    public Task(AbstractService... service){
        this.services = service;
    }

    public abstract void execute();
}
