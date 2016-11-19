/*


 */
package qmsjee.entities.entity;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tomek
 */
@Entity
@DiscriminatorValue("COMPONENT")
@Table
@NamedQueries({
    @NamedQuery(name = "Component.findByUid", query = "SELECT c FROM Component c WHERE c.uid = :uid"),
    @NamedQuery(name = "Component.findBySearchCriteria", query = "SELECT c FROM Component c WHERE c.name LIKE :name AND c.referenceNumber LIKE :referenceNumber ORDER BY c.name ASC")
})
public class Component extends Item {

    @ManyToMany(mappedBy = "items")
    private List<Issue> issues;
    private static final long serialVersionUID = 7612352248462954142L;
    @NotNull
    @Size(max = 60)
    private String name;
    private boolean finalProduct;
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public boolean isFinalProduct() {
        return finalProduct;
    }

    public void setFinalProduct(boolean finalProduct) {
        this.finalProduct = finalProduct;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
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
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entity.Components[ id=" + super.getId() + " ]";
    }
}
