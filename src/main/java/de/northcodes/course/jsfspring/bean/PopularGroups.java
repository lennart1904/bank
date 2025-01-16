package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.northcodes.course.jsfspring.model.Group;
import de.northcodes.course.jsfspring.service.GroupService;

import java.util.List;

@RequestScoped
@Component
@ManagedBean
public class PopularGroups {

    @Autowired
    private GroupService groupService;

    public List<Group> getGroups() {
        return groupService.getAllGroups();
    }
}
