/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@Named
@ApplicationScoped
public class UserListMBean implements Serializable {

    @Inject
    IUserService userService;
    
    private List<AppUser> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        users = userService.findAll();
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

}
