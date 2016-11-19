package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@Named
public class UserService extends GenericService<AppUser, Long> implements IUserService, Serializable {

    private static final long serialVersionUID = 7894108465192235879L;

    public UserService() {
        super(AppUser.class);
    }

    @Override
    public AppUser save(AppUser entity) {
        try {
            super.getUtx().begin();
            entity.setPassword(md5(entity.getPassword()));
            super.getEm().persist(entity);
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "AppUser {0} save in DB", entity);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity;
    }

    @Override
    public AppUser login(String login, String password) {
        List<AppUser> list = this.findAll();
        if (list != null) {
            for (AppUser appUser : list) {
                try {
                    if (appUser.getLogin().equals(login) && appUser.getPassword().equals(md5(password))) {
                        return appUser;
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public boolean logout(AppUser user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<AppUser> findAllExceptMe(AppUser me) {
        List<AppUser> results = new ArrayList<>();
        TypedQuery<AppUser> query = super.getEm().createQuery("SELECT c FROM AppUser c WHERE c.id != :id ORDER BY c.surname ASC", AppUser.class);
        results = query.setParameter("id", me.getId()).getResultList();
        Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "System recived all users except {0}", me.getLogin());
        return results;
    }

    public boolean usernameDouble(String login) {
        AppUser results = null;
        TypedQuery<AppUser> query = super.getEm().createQuery("SELECT c FROM AppUser c WHERE c.login = :login", AppUser.class);
        try {
            results = query.setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(GenericService.class.getName()).log(Level.WARNING, "Login is doubled", login);
            return false;
        }
        return true;

    }

    @Override
    public void updatePassword(AppUser user, String password) {
        try {
            user.setPassword(md5(password));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.setResetPwd(false);
        this.update(user);
    }

    public void resetPassword(AppUser user, String password) {
        try {
            user.setPassword(md5(password));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.setResetPwd(true);
        this.update(user);
    }

    private String md5(String input) throws UnsupportedEncodingException {
        String md5 = null;

        if (null == input) {
            return null;
        }

        try {

            byte[] bytesOfPassword = input.getBytes("UTF-8");

            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");

            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());

            //Converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
