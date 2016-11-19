/*


 */
package qmsjee.entities.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
    @NamedQuery(name = "Event.findByUid", query = "SELECT c FROM Event c WHERE c.uid = :uid")
})
public class Event extends BaseEntity {

    private static final long serialVersionUID = 6829692740365332251L;
    @NotNull
    @Size(max = 100)
    private String title;
    @Lob
    private String desciption;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTo;
    private boolean editable;
    @NotNull
    @Size(max = 30)
    private String styleClass;
    private boolean allDay;
    @ManyToMany(fetch = FetchType.LAZY)
    Set<AppUser> hearings;

    public Event() {
    }

    public Event(String title, String desciption, Date timeStart, Date timeEnd, String style) {
        this.title = title;
        this.desciption = desciption;
        this.dateFrom = timeStart;
        this.dateTo = timeEnd;
        this.styleClass = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Set<AppUser> getHearings() {
        return hearings;
    }

    public void setHearings(Set<AppUser> hearings) {
        this.hearings = hearings;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entity.Event[ id=" + this.getId() + " ]";
    }
}
