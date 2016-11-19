/*
 * To change super license header, choose License Headers in Project Properties.
 * To change super template file, choose Tools | Templates

 */
package qmsjee.entities.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import qmsjee.entities.common.BaseEntity;

/**
 *
 * @author darlotom
 */
@Entity
public class Comment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Lob
    @NotNull
    @Size(max = 255)
    private String content;
    @NotNull
    @ManyToOne
    private AppUser creator;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
    @NotNull
    @ManyToOne
    private Item item;

    public Comment() {
    }

    public Comment(AppUser creator, Item item) {
        this.creator = creator;
        this.item = item;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Item getIssue() {
        return item;
    }

    public void setIssue(Item issue) {
        this.item = issue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - super method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entities.Comment[ id=" + super.getId() + " ]";
    }

}
