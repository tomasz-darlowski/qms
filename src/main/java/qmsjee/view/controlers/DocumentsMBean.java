/*


 */
package qmsjee.view.controlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.DateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import qmsjee.entities.entity.FileEntity;
import qmsjee.entities.entity.Issue;
import qmsjee.entities.entity.Item;
import qmsjee.services.entityServices.interfaces.IFileService;
import qmsjee.services.entityServices.interfaces.IItemService;
import qmsjee.view.model.FileDataModel;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class DocumentsMBean implements Serializable {

    private static final long serialVersionUID = -5304986897310068046L;

    @Inject
    IItemService itemService;
    @Inject
    IFileService fileService;
    private FileDataModel dataModel;
    private FileEntity selectedFile;
    //    SEARCH FIELDS
    private String fileName;
    private String type = "any";
    private Date createAfter;
    private Date createBefore;
    private Item item;
    private boolean itemDisable;
    private String reportStatus;
    private boolean reportStatusDisable;
    private List<Item> items;
    private StreamedContent streamedFile;

    public void init() {
        fileName = "";
        reportStatus = "both";
    }

    public void setViewParams() {
        init();
        if (item == null) {
            type = "document";
            reportStatusDisable = true;
            itemDisable = false;
            items = itemService.findAll();
        } else {
            type = "report";
            reportStatusDisable = false;
            itemDisable = true;
        }

    }

    public void search() {
        List<FileEntity> tempData = new ArrayList<>();
        if (item != null) {
            tempData = fileService.findBySearchCriteriaWithItem(fileName, item.getUid(), type);
        } else {
            tempData = fileService.findBySearchCriteria(fileName, type);
        }
        if (tempData.size() > 0) {
            List<FileEntity> eraseDataList = new ArrayList<>();
            for (FileEntity fileEntity : tempData) {
                if (fileEntity.getCreateDate() != null && (createAfter != null || createBefore != null)) {
                    if (createAfter != null && createBefore == null && fileEntity.getCreateDate().before(createAfter)) {
                        eraseDataList.add(fileEntity);
                        continue;
                    } else if (createAfter == null && createBefore != null && fileEntity.getCreateDate().after(createBefore)) {
                        eraseDataList.add(fileEntity);
                        continue;
                    } else if (fileEntity.getCreateDate().before((createAfter == null) ? (new DateTime(1970, 1, 1, 0, 0).toDate()) : (createAfter))
                            && fileEntity.getCreateDate().after((createBefore == null) ? (new Date()) : (createBefore))) {
                        eraseDataList.add(fileEntity);
                        continue;
                    }
                }
                if (reportStatus.equals("nok")) {
                    if (fileEntity.isStatus()) {
                        eraseDataList.add(fileEntity);
                        continue;
                    }
                }
                if (reportStatus.equals("ok")) {
                    if (!fileEntity.isStatus()) {
                        eraseDataList.add(fileEntity);
                    }
                }
            }
            tempData.removeAll(eraseDataList);
        }
        dataModel = new FileDataModel(tempData);
    }

    public List<Item> complete(String query) {
        List<Item> results = new ArrayList<>();
        for (Item item : itemService.findAll()) {
            if (!(item instanceof Issue) && item.getReferenceNumber().startsWith(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public void typeChange() {
        reportStatusDisable = type.equals("document");
        reportStatus = "both";
    }

    public void downloadFile() {
        if (selectedFile != null) {
            InputStream stream = new ByteArrayInputStream(selectedFile.getContent());
            streamedFile = new DefaultStreamedContent(stream, selectedFile.getMimeType(), selectedFile.getFileName());
        }

    }

    public void delete() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        try {
            Item i = selectedFile.getItem();
            i.getFiles().remove(selectedFile);
            itemService.update(i);
            fileService.delete(selectedFile);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, selectedFile.getFileName() + " " + bundle.getString("cantDelete"), ""));
            return;
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, selectedFile.getFileName() + " " + bundle.getString("isDeleted"), ""));
        search();
    }

    //<editor-fold defaultstate="collapsed" desc="accessors">
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateAfter() {
        return createAfter;
    }

    public void setCreateAfter(Date createAfter) {
        this.createAfter = createAfter;
    }

    public Date getCreateBefore() {
        return createBefore;
    }

    public void setCreateBefore(Date createBefore) {
        this.createBefore = createBefore;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isItemDisable() {
        return itemDisable;
    }

    public void setItemDisable(boolean itemEnable) {
        this.itemDisable = itemEnable;
    }

    public boolean isReportStatusDisable() {
        return reportStatusDisable;
    }

    public void setReportStatusDisable(boolean reportStatusEnable) {
        this.reportStatusDisable = reportStatusEnable;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public FileDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(FileDataModel dataModel) {
        this.dataModel = dataModel;
    }

//</editor-fold>
    public void setSelectedFile(FileEntity selectedFile) {
        this.selectedFile = selectedFile;
    }

    public FileEntity getSelectedFile() {
        return selectedFile;
    }

    public StreamedContent getStreamedFileFile() {
        return streamedFile;
    }

    public StreamedContent getStreamedFile() {
        return streamedFile;
    }

    public void setStreamedFile(StreamedContent streamedFile) {
        this.streamedFile = streamedFile;
    }

}
