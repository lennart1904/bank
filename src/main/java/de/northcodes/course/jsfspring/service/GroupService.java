package de.northcodes.course.jsfspring.service;

import java.util.List;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;

public interface GroupService {

    /**
     * Erzeugt eine neue Gruppe mit den angegebenen Details.
     *
     * @param title       der Titel der Gruppe
     * @param topic       das Thema der Gruppe
     * @param description die Beschreibung der Gruppe
     * @param location    der Ort der Gruppe
     * @param owner       der Benutzer, der die Gruppe erstellt
     * @return die erstellte Gruppe
     */
    Group createGroup(String title, String topic, String description, String location, User owner);

    /**
     * Gibt eine Liste aller Gruppen zurück.
     *
     * @return eine Liste aller Gruppen
     */
    List<Group> getAllGroups();

    /**
     * Holt eine Gruppe basierend auf der ID.
     *
     * @param id die ID der Gruppe
     * @return die gefundene Gruppe
     */
    Group getGroupById(Long id);

    /**
     * Aktualisiert die Details einer bestehenden Gruppe.
     *
     * @param groupId     die ID der Gruppe
     * @param title       der neue Titel der Gruppe
     * @param topic       das neue Thema der Gruppe
     * @param description die neue Beschreibung der Gruppe
     * @param location    der neue Ort der Gruppe
     * @return die aktualisierte Gruppe
     */
    Group updateGroup(Long groupId, String title, String topic, String description, String location);

    /**
     * Löscht eine Gruppe basierend auf der ID.
     *
     * @param id die ID der Gruppe
     */
    void deleteGroup(Long id);

    /**
     * Fügt einen Benutzer als Mitglied zu einer Gruppe hinzu.
     *
     * @param groupId die ID der Gruppe
     * @param user    der Benutzer, der hinzugefügt wird
     */
    void addMember(Long groupId, User user);

    /**
     * Entfernt einen Benutzer aus einer Gruppe.
     *
     * @param groupId die ID der Gruppe
     * @param user    der Benutzer, der entfernt wird
     */
    void removeMember(Long groupId, User user);

    /**
     * Gibt eine Liste von Gruppen zurück, die von einem bestimmten Benutzer erstellt wurden.
     *
     * @param owner der Benutzer, der die Gruppen erstellt hat
     * @return eine Liste von Gruppen
     */
    List<Group> getGroupsOwnedByUser(User owner);

    /**
     * Gibt eine Liste von Gruppen zurück, in denen ein Benutzer Mitglied ist.
     *
     * @param member der Benutzer, der Mitglied ist
     * @return eine Liste von Gruppen
     */
    List<Group> getGroupsForMember(User member);
}
