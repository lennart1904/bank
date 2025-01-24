package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.service.GroupService;
import de.northcodes.course.jsfspring.bean.UserManager;

import java.io.Serializable;

@Component
@ViewScoped
@ManagedBean
public class GroupDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserManager userManager;

    private Group group;
    private long groupId;

    // Getter und Setter
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * Wird beim Laden der Seite aufgerufen, um die Gruppe zu initialisieren.
     */
    public void onload() {
        if (!userManager.isSignedIn()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must be signed in to create or edit a group.", null));
            return;
        }

        if (groupId == 0) {
            // Neue Gruppe erstellen
            group = new Group("", "", "", "", userManager.getCurrentUser());
        } else {
            // Existierende Gruppe laden
            group = groupService.getGroupById(groupId);
            if (group == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Group not found.", null));
                        group = new Group("", "", "", "", userManager.getCurrentUser());
            }
        }
    }

    /**
 * Speichert oder aktualisiert die Gruppe und navigiert zur Detailansicht oder zur Übersicht.
 */
public String submit() {
    try {
        if (groupId == 0) {
            // Neue Gruppe erstellen
            group.setOwner(userManager.getCurrentUser());
            Group createdGroup = groupService.createGroup(
                    group.getTitle(),
                    group.getTopic(),
                    group.getDescription(),
                    group.getLocation(),
                    group.getOwner()
            );

            // Sicherstellen, dass die erstellte Gruppe eine gültige ID hat
            if (createdGroup != null && createdGroup.getId() != null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Group created successfully.", null));
                return "group-details.xhtml?faces-redirect=true&groupId=" + createdGroup.getId();
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to create the group.", null));
                return null;
            }
        } else {
            // Bestehende Gruppe aktualisieren
            groupService.updateGroup(
                    group.getId(),
                    group.getTitle(),
                    group.getTopic(),
                    group.getDescription(),
                    group.getLocation()
            );

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Group updated successfully.", null));
            return "group-details.xhtml?faces-redirect=true&groupId=" + group.getId();
        }
    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error saving group: " + e.getMessage(), null));
        return null; // Bleibt auf der aktuellen Seite bei Fehler
    }
}


    /**
     * Validiert den Titel der Gruppe.
     */
    public void validateTitle(FacesContext context, Object value) {
        String title = (String) value;
        if (title == null || title.trim().isEmpty() || title.length() > 50) {
            throw new javax.faces.validator.ValidatorException(new FacesMessage("Title must be between 1 and 50 characters."));
        }
    }

    /**
     * Validiert den Ort der Gruppe.
     */
    public void validateLocation(FacesContext context, Object value) {
        String location = (String) value;
        if (location == null || location.trim().isEmpty() || location.length() > 50) {
            throw new javax.faces.validator.ValidatorException(new FacesMessage("Location must be between 1 and 50 characters."));
        }
    }
}
