package de.northcodes.course.jsfspring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = AbstractEntity.SHOP_PREFIX + "group")
public final class Group extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = AbstractEntity.SHOP_PREFIX + "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>(); // Initialisiert die Liste

    // Public no-arg constructor for JPA and external access
    public Group() {}

    // Public constructor for initializing a Group instance
    public Group(String title, String topic, String description, String location, User owner) {
        this.title = title;
        this.topic = topic;
        this.description = description;
        this.location = location;
        this.owner = owner;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        int i = description.indexOf('.');
        if (i >= 0 && i < 100) {
            return description.substring(0, i + 1);
        } else {
            return description.substring(0, Math.min(description.length(), 100)) + "...";
        }
    }

    public String getLocation() {
        return location;
    }

    public User getOwner() {
        return owner;
    }

    public List<User> getMembers() {
        return members;
    }

    // Setters for updating fields
    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    // Add member to the group
    public void addMember(User member) {
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    // Remove member from the group
    public void removeMember(User member) {
        members.remove(member);
    }

    /**
     * Validates the state of the Group entity.
     * Ensures that required fields (like owner) are set.
     */
    public void validate() {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty.");
        }
    }

    @Override
    public String toString() {
        return "Group ID: " + this.getId() + ", Title: " + this.getTitle() + ", Topic: " + this.getTopic();
    }
}
