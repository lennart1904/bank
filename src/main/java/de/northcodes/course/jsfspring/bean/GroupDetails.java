package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.model.User;
import de.northcodes.course.jsfspring.service.GroupService;

import java.io.Serializable;

@Component
@ViewScoped
@ManagedBean
public class GroupDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserManager userManager; // Für den aktuellen Benutzer

    private Group group;
    private Long groupId;

    @PostConstruct
    public void init() {
        // Initialize the group property
        group = new Group();
    }

    // Getter und Setter
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * Wird beim Laden der Seite aufgerufen, um die Gruppe zu initialisieren.
     * Wenn eine gültige groupId vorhanden ist, wird die entsprechende Gruppe geladen.
     * Andernfalls wird eine neue Gruppe erstellt.
     */
    public void onload() {
        if (groupId != null && groupId > 0) {
            // Load an existing group based on the ID
            group = groupService.getGroupById(groupId);
            if (group == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Gruppe nicht gefunden. Bitte überprüfen Sie die ID.");
                group = new Group(); // Fallback to prevent NullPointerException
            }
        } else {
            // No ID provided: Initialize a new group
            group = new Group();
        }
    }

    /**
     * Fügt den aktuellen Benutzer als Mitglied zur Gruppe hinzu.
     */
    public void addCurrentUserAsMember() {
        User currentUser = userManager.getCurrentUser();
        if (currentUser != null) {
            groupService.addCurrentUserAsMember(group.getId(), currentUser);
            addMessage(FacesMessage.SEVERITY_INFO, "You have successfully joined the group.");
        } else {
            addMessage(FacesMessage.SEVERITY_ERROR, "You need to be signed in to join the group.");
        }
    }


    /**
     * Speichert oder aktualisiert die Gruppe und navigiert zurück zur Übersicht.
     *
     * @return Die Zielseite (Redirect zu "allgroups.xhtml") oder null bei Fehlern
     */
    public String submit() {
        try {
            // Besitzer der Gruppe sicherstellen
            if (group.getOwner() == null) {
                User currentUser = userManager.getCurrentUser();
                if (currentUser != null) {
                    group.setOwner(currentUser);
                } else {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Sie müssen angemeldet sein, um eine Gruppe zu erstellen.");
                    return null; // Bleibt auf der gleichen Seite
                }
            }

            // Gruppe validieren
            try {
                group.validate(); // Überprüft, ob alle erforderlichen Felder gesetzt sind
            } catch (IllegalArgumentException e) {
                addMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
                return null; // Bleibt auf der gleichen Seite
            }

            if (groupId == null || groupId == 0) {
                // Neue Gruppe erstellen
                groupService.createGroup(
                        group.getTitle(),
                        group.getTopic(),
                        group.getDescription(),
                        group.getLocation(),
                        group.getOwner()
                );
                addMessage(FacesMessage.SEVERITY_INFO, "Gruppe erfolgreich erstellt.");
            } else {
                // Bestehende Gruppe aktualisieren
                groupService.updateGroup(
                        group.getId(),
                        group.getTitle(),
                        group.getTopic(),
                        group.getDescription(),
                        group.getLocation()
                );
                addMessage(FacesMessage.SEVERITY_INFO, "Gruppe erfolgreich aktualisiert.");
            }
            return "allgroups.xhtml?faces-redirect=true"; // Weiterleitung zur Gruppenübersicht
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Speichern der Gruppe: " + e.getMessage());
            return null; // Bleibt auf der gleichen Seite
        }
    }

    /**
     * Fügt eine Nachricht zum FacesContext hinzu.
     *
     * @param severity Schweregrad der Nachricht (INFO, WARN, ERROR)
     * @param detail   Detailtext der Nachricht
     */
    private void addMessage(FacesMessage.Severity severity, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, detail, null));
    }
}
