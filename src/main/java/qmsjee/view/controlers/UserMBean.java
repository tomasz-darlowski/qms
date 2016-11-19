/*


 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@Named("userMBean")
@SessionScoped
public class UserMBean implements Serializable {

    private static final long serialVersionUID = 4707542960458305857L;
    @Inject
    private IUserService userService;
    private AppUser user;
    private String login;
    private String password;
    private String newpassword;
    private String repeatpassword;
    private Locale locale;
    private boolean newPassword;

    public UserMBean() {
    }

    @PostConstruct
    public void init() {
        repeatpassword = "";
        newpassword = "";
    }

    public String loginAction() {
        user = userService.login(login, password);
        if (user != null && user.isResetPwd()) {
            if (newpassword.equals(repeatpassword) && !newpassword.isEmpty() && !repeatpassword.isEmpty()) {
                userService.updatePassword(user, newpassword);
                return "dashboard";
            } else {
                if (newpassword.isEmpty() && repeatpassword.isEmpty()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("writenewpass"), ""));
                    return null;
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("pwdnotequal"), ""));
                    return null;
                }
            }
        }
        if (user == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("wrongcredentials"), ""));
            return null;
        }
        return "dashboard";
    }

    public void checkMessages() {
        FacesContext context = FacesContext.getCurrentInstance();
        String message = (String) context.getExternalContext().getSessionMap().remove("message");
        if (message != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }

    public String logout() {
        user = null;
        return "login";
    }

    public void setNewPassword() {

    }

    public void changeLanguage(String locale) {
        user.setLocale(locale);
        userService.update(user);
        this.locale = new Locale(locale);
    }

    public boolean isLoggedIn() {
        if (user == null) {
            return false;
        }
        return true;
    }

    public String newUserAction() {
        userService.save(getUser());
        if (getUser().getId() == null) {
            return null;
        }
        return null;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getRepeatpassword() {
        return repeatpassword;
    }

    public void setRepeatpassword(String repeatpassword) {
        this.repeatpassword = repeatpassword;
    }

    public boolean isNewPassword() {
        return newPassword;
    }

    public void setNewPassword(boolean newPassword) {
        this.newPassword = newPassword;
    }

}
