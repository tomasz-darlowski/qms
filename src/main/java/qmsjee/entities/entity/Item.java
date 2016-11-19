/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.entities.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import qmsjee.entities.common.BaseEntity;

/**
 *
 * @author Tomek
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "itemType")
public abstract class Item extends BaseEntity {

    @Column(unique = true)
    @Size(max = 30)
    private String referenceNumber;
    @Lob
    private String description;
    @ManyToMany
    private List<Component> relatedComponents;
    @OneToMany(fetch = FetchType.LAZY)
    private List<FileEntity> files;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifyDate;
    @NotNull
    @ManyToOne
    private AppUser createBy;
    @ManyToOne
    private AppUser modifyBy;

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Component> getRelatedComponents() {
        return relatedComponents;
    }

    public void setRelatedComponents(List<Component> relatedComponents) {
        this.relatedComponents = relatedComponents;
    }

    public List<FileEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileEntity> files) {
        this.files = files;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public AppUser getCreateBy() {
        return createBy;
    }

    public void setCreateBy(AppUser createBy) {
        this.createBy = createBy;
    }

    public AppUser getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(AppUser modifyBy) {
        this.modifyBy = modifyBy;
    }

}
