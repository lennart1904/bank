package de.northcodes.course.jsfspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;
import de.northcodes.course.jsfspring.persistence.GroupRepository;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group createGroup(String title, String topic, String description, String location, User owner) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }

        Group group = new Group(title, topic, description, location, owner);
        Group savedGroup = groupRepository.save(group);
        System.out.println("Group created: " + savedGroup);
        return savedGroup;
    }

    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = (List<Group>) groupRepository.findAll();
        System.out.println("Fetched all groups: " + groups.size() + " groups found.");
        return groups;
    }

    @Override
    public Group getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + id));
        // Initialize the members collection
        group.getMembers().size();
        return group;
    }

    @Override
    public Group updateGroup(Long groupId, String title, String topic, String description, String location) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + groupId));

        if (title != null && !title.trim().isEmpty()) {
            group.setTitle(title);
        }
        if (topic != null && !topic.trim().isEmpty()) {
            group.setTopic(topic);
        }
        if (description != null && !description.trim().isEmpty()) {
            group.setDescription(description);
        }
        if (location != null && !location.trim().isEmpty()) {
            group.setLocation(location);
        }

        Group updatedGroup = groupRepository.save(group);
        System.out.println("Group updated: " + updatedGroup);
        return updatedGroup;
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + id));
        groupRepository.deleteById(id);
        System.out.println("Group deleted with ID: " + id);
    }

    @Override
    public void addMember(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + groupId));
        group.addMember(user);
        groupRepository.save(group);
        System.out.println("Member added to group: " + group);
    }

    @Override
    public void removeMember(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + groupId));
        group.removeMember(user);
        groupRepository.save(group);
        System.out.println("Member removed from group: " + group);
    }

    @Override
    public List<Group> getGroupsOwnedByUser(User owner) {
        List<Group> groups = groupRepository.findByOwner(owner);
        System.out.println("Groups owned by user: " + owner.getUsername() + ", count: " + groups.size());
        return groups;
    }

    @Override
    public List<Group> getGroupsForMember(User member) {
        List<Group> groups = groupRepository.findByMembersContaining(member);
        System.out.println("Groups for member: " + member.getUsername() + ", count: " + groups.size());
        return groups;
    }

    @Override
    public void addCurrentUserAsMember(Long groupId, User currentUser) {
        Group group = groupRepository.findById(groupId).orElseThrow(() ->
                new IllegalArgumentException("Group not found with ID: " + groupId));

        // Print the current members of the group
        System.out.println("Current members of the group: " + group.getMembers());

        // Check if the current user is already a member
        if (group.getMembers().contains(currentUser)) {
            throw new IllegalArgumentException("User is already a member of the group.");
        }

        // Check if the current user is the owner
        if (group.getOwner().equals(currentUser)) {
            throw new IllegalArgumentException("Owner cannot be a member of the group.");
        }

        group.addMember(currentUser);
        groupRepository.save(group);
        System.out.println("Member added to group: " + group);
    }
}
