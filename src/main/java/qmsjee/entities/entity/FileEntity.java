/*


 */
package qmsjee.entities.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import qmsjee.entities.common.BaseEntity;

/**
 *
 * @author darlotom
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name = "FileEntity.findByUid", query = "SELECT c FROM FileEntity c WHERE c.uid = :uid"),
    @NamedQuery(name = "FileEntity.findByTypeANDrelatedUID", query = "SELECT c FROM FileEntity c WHERE c.docType = :docType AND c.relatedUID = :relatedUID"),
    @NamedQuery(name = "FileEntity.findBySearchCriteriaWithItem", query = "SELECT c FROM FileEntity c WHERE c.fileName LIKE :fileName AND c.relatedUID = :relatedUID AND c.docType = :docType"),
    @NamedQuery(name = "FileEntity.findBySearchCriteria", query = "SELECT c FROM FileEntity c WHERE c.fileName LIKE :fileName AND c.docType = :docType")
})
public class FileEntity extends BaseEntity {

    private static final long serialVersionUID = -2833612969372474585L;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    protected byte[] content;
    @NotNull
    @Size(max = 30)
    private String mimeType;
    @NotNull
    private String fileName;
    @NotNull
    @Size(max = 30)
    private String docType;
    @Size(max = 255)
    private String description;
    private boolean status;
    //Related item
    private String relatedUID;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifyDate;
    @ManyToOne
    private AppUser createBy;
    @ManyToOne
    private AppUser modifyBy;
    @ManyToOne
    private Item item;

    public FileEntity() {
        this.setUid(UUID.randomUUID().toString());
    }

    public FileEntity(byte[] content, String mimeType, String fileName, String docType, String description, boolean status) {
        this.setUid(UUID.randomUUID().toString());
        this.content = content;
        this.mimeType = mimeType;
        this.fileName = fileName;
        this.docType = docType;
        this.description = description;
        this.status = status;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRelatedUID() {
        return relatedUID;
    }

    public void setRelatedUID(String relatedUID) {
        this.relatedUID = relatedUID;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FileEntity)) {
            return false;
        }
        FileEntity other = (FileEntity) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "qmsjee.entity.FileEntity(" + this.docType + ")[ id=" + super.getId() + " ]";
    }
}
