package de.northcodes.course.jsfspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;
import de.northcodes.course.jsfspring.persistence.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group createGroup(String title, String topic, String description, String location, User owner) {
        Group group = new Group(title, topic, description, location, owner);
        return groupRepository.save(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return (List<Group>) groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Group updateGroup(Long groupId, String title, String topic, String description, String location) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
            group.setTitle(title);
            group.setTopic(topic);
            group.setDescription(description);
            group.setLocation(location);
            return groupRepository.save(group);
        }
        return null;
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void addMember(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
            group.addMember(user);
            groupRepository.save(group);
        }
    }

    @Override
    public void removeMember(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
            group.removeMember(user);
            groupRepository.save(group);
        }
    }

    @Override
    public List<Group> getGroupsOwnedByUser(User owner) {
        return groupRepository.findByOwner(owner);
    }

    @Override
    public List<Group> getGroupsForMember(User member) {
    return groupRepository.findByMembersContaining(member);
    }

    @Override
    public List<Group> findByMembersContaining(User member) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByMembersContaining'");
    }

}
