/*
 
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.classes.Invitation;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.EmailService;
import qmsjee.services.entityServices.impl.EventService;
import qmsjee.services.entityServices.impl.UserService;
import qmsjee.view.model.SchedulerModel;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class InvitationMBean implements Serializable {

    private static final long serialVersionUID = -8002360421540390969L;
    private List<Invitation> invitations = new ArrayList<>();
    private List<AppUser> allUsers;
    private AppUser selectedUser;
    @Inject
    private UserService userService;
    @Inject
    private EventService eventService;
    @Inject
    EmailService emailService;
    @Inject
    SchedulerModel schedulerModel;
    private Invitation inv = new Invitation();

    public InvitationMBean() {
    }

    @PostConstruct
    public void init() {
        allUsers = userService.findAll();
        inv = new Invitation();
    }

    public void addInvitation() {
        invitations.add(inv);
        allUsers.remove(inv.getUser());
        inv = new Invitation();
    }

    public void removeInvitation() {
        allUsers.add(inv.getUser());
        invitations.remove(inv);
        inv = new Invitation();
    }

    public String sendInvitations() throws Exception {
        if (!invitations.isEmpty()) {
            emailService.sendInvitations(invitations, schedulerModel.getEvent());
            for (Invitation invitation : invitations) {
                schedulerModel.getEvent().getHearings().add(invitation.getUser());
            }
            eventService.update(schedulerModel.getEvent());
            invitations.clear();
            init();
            return "view";
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and getters">
    public List<Invitation> getInvitation() {
        return invitations;
    }

    public void setInvitation(List<Invitation> invitation) {
        this.invitations = invitation;
    }

    public List<AppUser> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<AppUser> allUsers) {
        this.allUsers = allUsers;
    }

    public AppUser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(AppUser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Invitation getInv() {
        return inv;
    }

    public void setInv(Invitation inv) {
        this.inv = inv;
    }
    //</editor-fold>
}
