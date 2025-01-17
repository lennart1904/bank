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

    private long groupId;

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

    public void onload() {
        if (groupId == 0) {
            // New Group Creation
            group = new Group();
        } else {
            // Load existing group from the service
            group = groupService.getGroupById(groupId);
        }
    }

    public String submit() {
        if (groupId == 0) {
            groupService.createGroup(
                group.getTitle(),
                group.getTopic(),
                group.getDescription(),
                group.getLocation(),
                group.getOwner()
            );
        } else {
            groupService.updateGroup(
                group.getId(),
                group.getTitle(),
                group.getTopic(),
                group.getDescription(),
                group.getLocation()
            );
        }
        return "allgroups.xhtml?faces-redirect=true";
    }

    public void validateTitle(FacesContext context, Object value) {
        String title = (String) value;
        if (title == null || title.trim().isEmpty() || title.length() > 50) {
            throw new javax.faces.validator.ValidatorException(new FacesMessage("Title must be between 1 and 50 characters."));
        }
    }

    public void validateLocation(FacesContext context, Object value) {
        String location = (String) value;
        if (location == null || location.trim().isEmpty() || location.length() > 50) {
            throw new javax.faces.validator.ValidatorException(new FacesMessage("Location must be between 1 and 50 characters."));
        }
    }
}
