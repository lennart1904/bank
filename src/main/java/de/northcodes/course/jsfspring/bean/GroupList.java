package de.northcodes.course.jsfspring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.service.GroupService;

@Component // Spring-kompatible Bean
@ViewScoped // JSF-Scope
public class GroupList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private GroupService groupService;

    private List<Group> groups;

    public List<Group> getGroups() {
        if (groups == null) {
            groups = groupService.getAllGroups(); // Lädt alle Gruppen aus dem Service
        }
        return groups;
    }

    // Methode, um die Gruppenliste zu aktualisieren
    public void refreshGroups() {
        groups = groupService.getAllGroups(); // Aktuelle Gruppenliste neu laden
        System.out.println("Groups refreshed: " + groups.size()); // Debug-Log
    }
}
