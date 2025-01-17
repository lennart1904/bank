package de.northcodes.course.jsfspring.service;

import java.util.List;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;

public interface GroupService {

    Group createGroup(String title, String topic, String description, String location, User owner);

    List<Group> getAllGroups();

    Group getGroupById(Long id);

    Group updateGroup(Long groupId, String title, String topic, String description, String location);

    void deleteGroup(Long id);

    void addMember(Long groupId, User user);

    void removeMember(Long groupId, User user);

    List<Group> getGroupsOwnedByUser(User owner);

    List<Group> getGroupsForMember(User member);
    
}
