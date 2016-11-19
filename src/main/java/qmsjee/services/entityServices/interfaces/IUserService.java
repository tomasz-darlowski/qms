/*
 
 
 */
package qmsjee.services.entityServices.interfaces;

import qmsjee.entities.entity.AppUser;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IUserService extends IGenericService<AppUser, Long> {

    public AppUser login(String login, String poassword);

    public boolean logout(AppUser user);
    
    public void updatePassword(AppUser user, String password);
}
