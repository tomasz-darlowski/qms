/*
 
 
 */
package qmsjee.classes;

import qmsjee.entities.entity.AppUser;

/**
 *
 * @author Tomek
 */
public class Invitation {

    private AppUser user;
    private String note;

    public Invitation() {
    }

    public Invitation(AppUser user, String note) {
        this.user = user;
        this.note = note;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
