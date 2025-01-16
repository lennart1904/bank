package de.northcodes.course.jsfspring.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;

public interface GroupRepository extends CrudRepository<Group, Long> {

    // Retrieve all groups owned by a specific user
    List<Group> findByOwner(User owner);

    // Retrieve groups where a specific user is a member
    List<Group> findByMembersContaining(User member);
}
