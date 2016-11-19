/*


 */
package qmsjee.entities.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({
    @NamedQuery(name = "Supplier.findByUid", query = "SELECT c FROM Supplier c WHERE c.uid = :uid")
})
public class Supplier extends BaseEntity {

    private static final long serialVersionUID = 41833468372813274L;
    @NotNull
    @Size(max = 40)
    private String name;
    @Size(min = 3, max = 60)
    private String contactPerson;
    @NotNull
    @Size(min = 3, max = 200)
    private String address;
    @NotNull
    @Size(max = 80)
    private String email;
    @NotNull
    @Size(max = 30)
    private String telephone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entity.Customer[ id=" + super.getId() + " ]";
    }
}
