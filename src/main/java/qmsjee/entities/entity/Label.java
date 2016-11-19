/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.entities.entity;

import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import qmsjee.entities.common.BaseEntity;

/**
 *
 * @author toldi
 */
@Entity
public class Label extends BaseEntity {

    @NotNull
    @Size(max = 20)
    private String label;
    private int relatedIssuesCount;

    public Label() {
        this.relatedIssuesCount = 0;
    }

    public Label(String label) {
        this.label = label;
        this.relatedIssuesCount = 0;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getRelatedIssuesCount() {
        return relatedIssuesCount;
    }

    public void setRelatedIssuesCount(int relatedIssuesCount) {
        this.relatedIssuesCount = relatedIssuesCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Label)) {
            return false;
        }
        Label other = (Label) obj;
        return (super.getId() != null || other.getId() == null) && (super.getId() == null || super.getId().equals(other.getId()));
    }

}
