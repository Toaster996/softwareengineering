package de.dhbw.softwareengineering.digitaljournal.persistence;

import de.dhbw.softwareengineering.digitaljournal.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, String> {
    List<Friend> findAllByName(String name);

    @Transactional
    void deleteFriendByFriendNameEqualsAndNameEquals(String friend, String owner);
}
