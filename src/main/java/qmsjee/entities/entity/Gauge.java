/*


 */
package qmsjee.entities.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tomek
 */
@Entity
@DiscriminatorValue("GAUAGE")
@Table
@NamedQueries({
    @NamedQuery(name = "Gauage.findByUid", query = "SELECT c FROM Gauge c WHERE c.uid = :uid")
})
public class Gauge extends Item {

    private static final long serialVersionUID = 1361427015132319991L;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date validationDate;
    @NotNull
    @Max(60)
    private int validationPeriod = 6;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Event event;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gauge)) {
            return false;
        }
        Gauge other = (Gauge) object;
        return (super.getId() != null || other.getId() == null) && (super.getId() == null || super.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "qmsjee.entity.Gauage[ id=" + super.getId() + " ]";
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public int getValidationPeriod() {
        return validationPeriod;
    }

    public void setValidationPeriod(int validationPeriod) {
        this.validationPeriod = validationPeriod;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
