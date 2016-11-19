package qmsjee.view.controlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.DateTime;
import qmsjee.entities.entity.Component;
import qmsjee.entities.entity.Event;
import qmsjee.entities.entity.FileEntity;
import qmsjee.entities.entity.Gauge;
import qmsjee.enums.FileType;
import qmsjee.enums.Navigation;
import qmsjee.services.entityServices.interfaces.IComponentService;
import qmsjee.services.entityServices.interfaces.IEventService;
import qmsjee.services.entityServices.interfaces.IFileService;
import qmsjee.services.entityServices.interfaces.IGaugeService;
import qmsjee.view.controlers.common.CommonMBean;
import qmsjee.view.interfaces.IFileAttachable;
import qmsjee.view.interfaces.IPersistable;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class GauageMBean extends CommonMBean implements Serializable, IFileAttachable, IPersistable {

    private static final long serialVersionUID = -46263532240093674L;
    @Inject
    private IGaugeService gauageService;
    @Inject
    private IComponentService componentService;
    @Inject
    private UserMBean userMB;
    @Inject
    private FileMBean fileMB;
    @Inject
    private IFileService fileService;
    @Inject
    private IEventService eventService;
    //FIELDS
    private Gauge gauage;
    private String selectedComponentReference;
    private String gauageReference;
    private List<FileEntity> images;
    private List<FileEntity> docs;

    @PostConstruct
    public void init() {
        gauage = new Gauge();
        gauage.setActive(true);
        reinit();
    }

    public void reinit() {
        images = fileService.findByTypeANDrelatedUID(FileType.IMAGE.toString(), this.gauage.getUid());
        docs = fileService.findByTypeANDrelatedUID(FileType.DOCUMENT.toString(), this.gauage.getUid());
    }

    public List<Component> complete(String query) {
        List<Component> results = new ArrayList<>();
        for (Component comp : componentService.findAll()) {
            if (comp.getReferenceNumber().startsWith(query)) {
                results.add(comp);
            }
        }
        return results;
    }

    @Override
    public void setFileEntityData() {
        fileMB.getFileEntity().setRelatedUID(this.gauage.getUid());
        fileMB.getFileEntity().setCreateBy(userMB.getUser());
        this.getGauage().getFiles().add(fileMB.getFileEntity());
//        gauageService.update(this.getGauage());
        reinit();
    }

    @Override
    public void uploadDocument() throws IOException {
        fileMB.uploadFile(FileType.DOCUMENT, this.gauage);
        setFileEntityData();
        gauageService.update(this.getGauage());
        super.setDBMessage(Gauge.class.getSimpleName(), "isupdated");
    }

    @Override
    public void uploadImage() throws IOException {
        fileMB.uploadFile(FileType.IMAGE, this.gauage);
        setFileEntityData();
        gauageService.update(this.getGauage());
        super.setDBMessage(Gauge.class.getSimpleName(), "isupdated");
    }

    @Override
    public void uploadReport() throws IOException {
        fileMB.uploadFile(FileType.REPORT, this.gauage);
        setFileEntityData();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        if (fileMB.isReportStatus()) {
            this.gauage.setValidationDate(new Date());
            if (this.gauage.getEvent() == null) {
                Event gauageEvent = new Event(bundle.getString("gauge") + " " + this.getGauage().getReferenceNumber(), bundle.getString("validationExpireDay"), setExpireDate(this.getGauage()), setExpireDate(this.getGauage()), "gauge");
                gauageEvent.setAllDay(true);
                gauageEvent.setEditable(false);
                this.gauage.setEvent(gauageEvent);
            }
            this.gauage.setValidationDate(new Date());
            gauageService.update(this.gauage);
            super.setDBMessage("", "raportisuploaded");
        } else {
            this.gauage.setValidationDate(null);
            this.gauage.setEvent(null);
            gauageService.update(this.gauage);
        }
        super.setDBMessage(Gauge.class.getSimpleName(), "isupdated");
    }

    @Override
    public String createAndClose() {
        prepareData();
        gauageService.save(this.getGauage());
        super.setDBMessage(gauage.getClass().getSimpleName(), "isadded");
        return Navigation.GAUGES.toString();
    }

    @Override
    public String saveAndClose() {
        prepareData();
        gauageService.update(this.getGauage());
        super.setDBMessage(gauage.getClass().getSimpleName(), "isadded");
        return Navigation.GAUGES.toString();
    }

    public String save() {
        prepareData();
        gauageService.update(this.getGauage());
        super.setDBMessage(gauage.getClass().getSimpleName(), "isadded");
        return Navigation.VIEW.toString();
    }

    @Override
    public String createAndEdit() {
        prepareData();
        gauageService.save(this.getGauage());
        gauageReference = gauage.getReferenceNumber();
        super.setDBMessage(gauage.getClass().getSimpleName(), "isadded");
        return Navigation.EDIT.toString();
    }

    @Override
    public String delete() {
        gauageService.delete(this.getGauage());
        return Navigation.GAUGES.toString();
    }

    public void validateGauage() throws IOException {
        fileMB.uploadFile(FileType.REPORT, this.gauage);
        this.getGauage().getFiles().add(fileMB.getFileEntity());
        this.getGauage().setValidationDate(new Date());
        prepareData();
        gauageService.update(this.getGauage());

    }

    public String deactivate() {
        gauageService.disactive(gauage);
        super.setDBMessage(gauage.getClass().getSimpleName(), "isupdated");
        return null;
    }

    public String activate() {
        gauageService.active(gauage);
        super.setDBMessage(gauage.getClass().getSimpleName(), "isupdated");
        return null;
    }

    @Override
    public String removeFile() {

        try {
            if (fileMB.getFileEntity() != null) {
                this.getGauage().getFiles().remove(fileMB.getFileEntity());
                gauageService.update(gauage);
                fileService.delete(fileMB.getFileEntity());
            }
            reinit();
        } catch (Exception e) {
        } finally {
            reinit();
        }
        return null;
    }

    public Date setExpireDate(Gauge gauage) {
        DateTime validationDay = new DateTime(gauage.getValidationDate());
        return validationDay.plusMonths(gauage.getValidationPeriod()).toDate();
    }

    @Override
    public void prepareData() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        if (gauage.getUid() == null) {
            gauage.setCreateBy(userMB.getUser());
            gauage.setCreateDate(new Date());
        } else {
            gauage.setModifyBy(userMB.getUser());
            gauage.setModifyDate(new Date());
        }
        if (this.getGauage().getEvent() == null) {
            Event gauageEvent = new Event(bundle.getString("gauge") + " " + this.getGauage().getReferenceNumber(), bundle.getString("validationExpireDay"), setExpireDate(this.getGauage()), setExpireDate(this.getGauage()), "gauge");
            gauageEvent.setAllDay(true);
            gauageEvent.setEditable(false);
            this.getGauage().setEvent(gauageEvent);
        } else {
            this.getGauage().getEvent().setTitle(bundle.getString("gauge") + " " + this.getGauage().getReferenceNumber());
            this.getGauage().getEvent().setDateFrom(setExpireDate(this.getGauage()));
            this.getGauage().getEvent().setDateTo(setExpireDate(this.getGauage()));
        }
        List<Component> list = new ArrayList<>();
        if (this.getGauage().getRelatedComponents() != null) {
            Set<Component> set = new HashSet<>(this.getGauage().getRelatedComponents());
            list.addAll(set);
        }
        this.getGauage().setRelatedComponents(list);
    }

    public Gauge getGauage() {
        return gauage;
    }

    public void setGauage(Gauge gauage) {
        this.gauage = gauage;
    }

    public String getSelectedComponentReference() {
        return selectedComponentReference;
    }

    public void setSelectedComponentReference(String selectedComponentReference) {
        this.selectedComponentReference = selectedComponentReference;
    }

    public List<FileEntity> getImages() {
        return images;
    }

    public void setImages(List<FileEntity> images) {
        this.images = images;
    }

    public List<FileEntity> getDocs() {
        return docs;
    }

    public void setDocs(List<FileEntity> docs) {
        this.docs = docs;
    }

    public String getGauageReference() {
        return gauageReference;
    }

    public void setGauageReference(String gauageReference) {
        this.gauageReference = gauageReference;
    }
}
