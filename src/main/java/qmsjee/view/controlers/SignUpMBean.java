/*
 
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@Named
@RequestScoped
public class SignUpMBean implements Serializable {

    private static final long serialVersionUID = 7687937745760344843L;
    private AppUser user;
    String password;
    @Inject
    private IUserService userService;

    public SignUpMBean() {
    }

    @PostConstruct
    public void init() {
        user = new AppUser();
    }

    public void persistUser() {
        userService.save(user);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("result", new FacesMessage(FacesMessage.SEVERITY_INFO, "User " + user.getLogin() + " is added", ""));
        user = new AppUser();



    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
