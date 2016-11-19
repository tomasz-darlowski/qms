/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.RandomStringUtils;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.EmailService;
import qmsjee.services.entityServices.impl.UserService;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class UserManagerMBean implements Serializable {

    private List<AppUser> userList = new ArrayList<>();
    @Inject
    UserMBean userMBean;
    @Inject
    UserService userService;
    @Inject
    EmailService emailService;
    private AppUser newUser;

    @PostConstruct
    public void init() {
        userList = userService.findAllExceptMe(userMBean.getUser());
        newUser = new AppUser();
    }

    public void saveNewUser() throws Exception {
        String username = (newUser.getSurname().substring(0, 5) + newUser.getName().substring(0, 3)).toLowerCase();
        int i=0;
        while (true) {
            if (!(userService.usernameDouble(username))) {
                break;
            }else{
                username+=i;
            }
        }
        newUser.setLogin(username);
        newUser.setResetPwd(true);
        String newpass = RandomStringUtils.random(5, "abcdefghijklmopqstuvwxyz0123456789");
        newUser.setPassword(newpass);
        userService.save(newUser);
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, newUser.getLogin() + " - " + bundle.getString("isadded"), ""));
        emailService.sendReminder(newUser, newpass);
        init();
    }

    public void removeUser(long id) {
        AppUser usr = userService.findByID(id);
        if (!userService.delete(id)) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, usr.getLogin() + " " + bundle.getString("cantDelete"), ""));
        }
        init();
    }

    public void resetPasswordUser(long id) throws Exception {
        AppUser usr = userService.findByID(id);
        String newpass = RandomStringUtils.random(5, "abcdefghijklmopqstuvwxyz0123456789");
        userService.resetPassword(usr, newpass);
        emailService.sendReminder(usr, newpass);
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, usr.getLogin() + " - " + bundle.getString("passwordreseted"), ""));
    }

    public List<AppUser> getUserList() {
        return userList;
    }

    public void setUserList(List<AppUser> userList) {
        this.userList = userList;
    }

    public AppUser getNewUser() {
        return newUser;
    }

    public void setNewUser(AppUser newUser) {
        this.newUser = newUser;
    }

}
