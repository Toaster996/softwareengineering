package de.dhbw.softwareengineering.digitaljournal.util.Tasks;

import de.dhbw.softwareengineering.digitaljournal.business.AbstractService;

public interface Task {
    public void execute(AbstractService... service);
}
