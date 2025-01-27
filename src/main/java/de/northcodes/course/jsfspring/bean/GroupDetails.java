package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.service.GroupService;

import java.io.Serializable;

@Component
@ViewScoped
@ManagedBean
public class GroupDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private GroupService groupService;

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
     */
    public void onload() {
        if (groupId != null && groupId > 0) {
            // Lade die existierende Gruppe basierend auf der ID
            group = groupService.getGroupById(groupId);
            if (group == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Group not found. Please check the ID.");
            }
        } else {
            // Keine ID vorhanden: Neue Gruppe initialisieren
            group = new Group();
        }
    }

    /**
     * Speichert oder aktualisiert die Gruppe und navigiert zurück zur Übersicht.
     */
    public String submit() {
        try {
            if (groupId == null || groupId == 0) {
                // Neue Gruppe erstellen
                groupService.createGroup(
                        group.getTitle(),
                        group.getTopic(),
                        group.getDescription(),
                        group.getLocation(),
                        group.getOwner()
                );
                addMessage(FacesMessage.SEVERITY_INFO, "Group created successfully.");
            } else {
                // Bestehende Gruppe aktualisieren
                groupService.updateGroup(
                        group.getId(),
                        group.getTitle(),
                        group.getTopic(),
                        group.getDescription(),
                        group.getLocation()
                );
                addMessage(FacesMessage.SEVERITY_INFO, "Group updated successfully.");
            }
            return "allgroups.xhtml?faces-redirect=true"; // Redirect zur Übersicht
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Error saving group: " + e.getMessage());
            return null; // Bleibt auf der gleichen Seite
        }
    }

    /**
     * Fügt eine Nachricht zum FacesContext hinzu.
     */
    private void addMessage(FacesMessage.Severity severity, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, detail, null));
    }
}
