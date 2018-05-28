package de.dhbw.softwareengineering.digitaljournal.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public class UUIDGenerator {

    public static String generateUniqueUUID(JpaRepository repository) {
        UUID uuid = UUID.randomUUID();
        while (repository.existsById(uuid.toString())) {
            uuid = UUID.randomUUID();
        }
        return uuid.toString();
    }

}
