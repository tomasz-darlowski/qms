/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.entities.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author toldi
 */
@Entity
@DiscriminatorValue("COMPONENT")
@Table
@NamedQuery(name = "CountAll", query = "SELECT COUNT(i) FROM Issue i")
public class Issue extends Item {

    @NotNull
    @Column(unique = true)
    private long issueNumber;
    @NotNull
    @Size(max = 100)
    private String title;
    @NotNull
    @Size(max = 30)
    private String issueType;
    @NotNull
    @Size(max = 30)
    private String issuePriority;
    @NotNull
    @Size(max = 30)
    private String issueStatus;
    @ManyToOne
    private AppUser assigneTo;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date resolveDate;
    @JoinTable(name = "issue_has_labels")
    @ManyToMany
    private Collection<Label> labels;
    @JoinTable(name = "issue_has_relatedComponents")
    @ManyToMany
    private Collection<Component> items;

    @ManyToMany
    private Collection<Issue> linkedIssues;
    @ManyToMany(mappedBy = "linkedIssues")
    private List<Issue> hasLinkedIssues;

    public Issue() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public AppUser getAssigneTo() {
        return assigneTo;
    }

    public void setAssigneTo(AppUser assigneTo) {
        this.assigneTo = assigneTo;
    }

    public Collection<Label> getLabels() {
        return labels;
    }

    public void setLabels(Collection<Label> labels) {
        this.labels = labels;
    }

    public long getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(long issueNumber) {
        this.issueNumber = issueNumber;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }

    public Collection<Component> getItems() {
        return items;
    }

    public void setItems(Collection<Component> items) {
        this.items = items;
    }

    public Collection<Issue> getLinkedIssues() {
        return linkedIssues;
    }

    public void setLinkedIssues(Collection<Issue> linkedIssues) {
        this.linkedIssues = linkedIssues;
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
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entity.Issue[ id=" + super.getId() + " ]";
    }

}
