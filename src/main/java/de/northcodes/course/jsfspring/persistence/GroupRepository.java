package de.northcodes.course.jsfspring.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Findet alle Gruppen, die von einem bestimmten Benutzer erstellt wurden.
     *
     * @param owner der Benutzer, der die Gruppen erstellt hat
     * @return eine Liste von Gruppen
     */
    List<Group> findByOwner(User owner);

    /**
     * Findet alle Gruppen, in denen ein bestimmter Benutzer Mitglied ist.
     *
     * @param member der Benutzer, der Mitglied ist
     * @return eine Liste von Gruppen
     */
    List<Group> findByMembersContaining(User member);
}
