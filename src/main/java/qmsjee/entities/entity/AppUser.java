/*


 */
package qmsjee.entities.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import qmsjee.entities.common.BaseEntity;

/**
 *
 * @author Tomek
 */
@Entity
@Table
public class AppUser extends BaseEntity {

    private static final long serialVersionUID = -2188027928542041388L;
    @NotNull
    @Size(min = 3, max = 40)
    private String name;
    @NotNull
    @Size(min = 3, max = 40)
    private String surname;
    @NotNull
    private String password;
    @NotNull
    @Column(unique = true)
    private String login;
    @NotNull
    @Size(min = 3, max = 80)
    private String email;
    private boolean admin;
    private boolean resetPwd;
    @NotNull
    private String locale;

    public AppUser() {
        this.locale = "en";
        password = "";
    }

    public AppUser(String name, String surname, String password, String login, String email, boolean admin) {
        this();
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.email = email;
        this.admin = admin;
    }

    public boolean isResetPwd() {
        return resetPwd;
    }

    public void setResetPwd(boolean resetPwd) {
        this.resetPwd = resetPwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppUser)) {
            return false;
        }
        AppUser other = (AppUser) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qms.entity.User[ id=" + this.getId() + " ]";
    }
}
