package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
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
     * Fügt eine Nachricht zum FacesContext hinzu.
     *
     * @param severity Schweregrad der Nachricht (INFO, WARN, ERROR)
     * @param detail   Detailtext der Nachricht
     */
    private void addMessage(FacesMessage.Severity severity, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, detail, null));
    }
}
