package de.dhbw.softwareengineering.digitaljournal.business;

import de.dhbw.softwareengineering.digitaljournal.domain.Friend;
import de.dhbw.softwareengineering.digitaljournal.domain.form.CreateFriend;
import de.dhbw.softwareengineering.digitaljournal.persistence.FriendRepository;
import de.dhbw.softwareengineering.digitaljournal.util.UUIDGenerator;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService extends AbstractService{
    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public List<Friend> findAll(String name){
        return friendRepository.findAllByName(name);
    }

    public List<Friend> getAllApproved(String name){
        List<Friend> allFriends = friendRepository.findAllByName(name);
        List<Friend> approvedFriends = new ArrayList<>();
        for(Friend friend : allFriends){
            if(friend.isApproved())
                approvedFriends.add(friend);
        }
        return approvedFriends;
    }

    public boolean save(CreateFriend createFriend, Principal principal, UserService userService){
        ///TODO XSS
        Friend friend = new Friend();
        String friendName = createFriend.getUsername();
        if(userService.exists(friendName) && !principal.getName().equals(friendName) && !hasFriend(principal.getName(), friendName)) {
            friend.setId(UUIDGenerator.generateUniqueUUID(friendRepository));
            friend.setName(principal.getName());
            friend.setFriendName(createFriend.getUsername());
            List<Friend> friendFriends = friendRepository.findAllByName(friendName);
            for(Friend friendFriend : friendFriends){
                if(friendFriend.getFriendName().equals(principal.getName())){
                    updateFriendFriend(friendFriend);
                    friend.setApproved(true);
                }
            }
            friendRepository.save(friend);
            return true;
        } else {
            return false;
        }
    }

    private void updateFriendFriend(Friend friendFriend) {
        friendFriend.setApproved(true);
        friendRepository.save(friendFriend);
    }

    public boolean hasFriend(String username, String friendname){
        return friendRepository.existsByNameEqualsAndAndFriendNameEquals(username, friendname);
    }

    public void remove(String friend, String owner) {
        friendRepository.deleteFriendByFriendNameEqualsAndNameEquals(friend, owner);
    }
}
