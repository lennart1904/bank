package de.northcodes.course.jsfspring.bean;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.northcodes.course.jsfspring.model.User;
import de.northcodes.course.jsfspring.service.UserService;

import java.io.Serializable;

@SessionScoped
@Component
@ManagedBean
public class UserManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private UserService userService;

    private User currentUser;

    public boolean isSignedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String signIn(String username, String password) {
        User user = userService.getUser(username);
        if (user == null || !password.equals(user.getPassword())) {
        	 FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage("Please enter a valid username and password."));
            return "sign-in";
        }

        currentUser = user;
        return "allgroups";
    }

    public String signOut() {
        // Invalidate the session to remove any session state
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        // Redirect to the sign-in page
        return "sign-in?faces-redirect=true";
    }

    public String save(User user) {
        userService.saveUser(user);
        currentUser = user;
        return "allgroups";
    }
}
