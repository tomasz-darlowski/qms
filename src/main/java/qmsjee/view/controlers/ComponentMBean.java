package qmsjee.view.controlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.Component;
import qmsjee.entities.entity.FileEntity;
import qmsjee.entities.entity.Gauge;
import qmsjee.entities.entity.Issue;
import qmsjee.enums.FileType;
import qmsjee.enums.Navigation;
import qmsjee.services.entityServices.impl.FileService;
import qmsjee.services.entityServices.impl.GaugeService;
import qmsjee.services.entityServices.impl.IssueService;
import qmsjee.services.entityServices.interfaces.IComponentService;
import qmsjee.view.controlers.common.CommonMBean;
import qmsjee.view.interfaces.IFileAttachable;
import qmsjee.view.interfaces.IPersistable;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class ComponentMBean extends CommonMBean implements Serializable, IFileAttachable, IPersistable {

    private static final long serialVersionUID = 373631006594351707L;
    @Inject
    private IComponentService componentService;
    @Inject
    private FileMBean fileMB;
    @Inject
    private FileService fileService;
    @Inject
    private GaugeService gaugeService;
    @Inject
    private UserMBean userMB;
    private Component component;
    private List<FileEntity> images;
    private List<FileEntity> docs;
    private List<Gauge> gauges;
    private String selectedComponentReference;
    private String selectedGaugeReference;
    private String selectedIssueId;

    public ComponentMBean() {
    }

    @PostConstruct
    public void init() {
        component = new Component();
        component.setActive(true);
        reinit();
    }

    public void reinit() {
        images = fileService.findByTypeANDrelatedUID(FileType.IMAGE.toString(), this.component.getUid());
        docs = fileService.findByTypeANDrelatedUID(FileType.DOCUMENT.toString(), this.component.getUid());
        gauges = gaugeService.findByComponent(component);
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

    public String deactivate() {
        componentService.disactive(component);
        super.setDBMessage(component.getClass().getSimpleName(), "isupdated");
        return null;
    }

    public String activate() {
        componentService.active(component);
        super.setDBMessage(component.getClass().getSimpleName(), "isupdated");
        return null;
    }

    @Override
    public void setFileEntityData() {
        fileMB.getFileEntity().setRelatedUID(this.component.getUid());
        fileMB.getFileEntity().setCreateBy(userMB.getUser());
        this.getComponent().getFiles().add(fileMB.getFileEntity());
        componentService.update(this.getComponent());
        reinit();
    }

    @Override
    public void uploadDocument() throws IOException {
        fileMB.uploadFile(FileType.DOCUMENT, this.component);
        super.setDBMessage(Component.class.getSimpleName(), "isupdated");
        setFileEntityData();
    }

    @Override
    public void uploadImage() throws IOException {
        fileMB.uploadFile(FileType.IMAGE, this.component);
        super.setDBMessage(Component.class.getSimpleName(), "isupdated");
        setFileEntityData();
    }

    @Override
    public void uploadReport() throws IOException {
        fileMB.uploadFile(FileType.REPORT, this.component);
        super.setDBMessage("", "raportisuploaded");
        setFileEntityData();
    }

    @Override
    public String createAndClose() {
        prepareData();
        componentService.save(this.getComponent());
        super.setDBMessage(Component.class.getSimpleName(), "isadded");
        return Navigation.COMPONENTS.toString();
    }

    @Override
    public String createAndEdit() {
        prepareData();
        componentService.save(this.getComponent());
        super.setDBMessage(Component.class.getSimpleName(), "isadded");
        return Navigation.EDIT.toString();
    }

    @Override
    public String saveAndClose() {
        prepareData();
        componentService.update(this.getComponent());
        super.setDBMessage(Component.class.getSimpleName(), "isupdated");
        return Navigation.COMPONENTS.toString();
    }

    public String save() {
        prepareData();
        componentService.update(this.getComponent());
        super.setDBMessage(Component.class.getSimpleName(), "isupdated");
        return Navigation.VIEW.toString();
    }

    @Override
    public void prepareData() {
        if (component.getUid() == null) {
            component.setCreateBy(userMB.getUser());
            component.setCreateDate(new Date());
        } else {
            component.setModifyBy(userMB.getUser());
            component.setModifyDate(new Date());
        }
        List<Component> list = new ArrayList<>();
        if (this.getComponent().getRelatedComponents() != null) {
            Set<Component> set = new HashSet<>(this.getComponent().getRelatedComponents());
            list.addAll(set);
        }
        for (Component comp : list) {
            if (comp.getId().equals(this.component.getId())) {
                list.remove(comp);
            }
        }
        this.getComponent().setRelatedComponents(list);
    }

    @Override
    public String delete() {
        return Navigation.COMPONENTS.toString();
    }

    @Override
    public String removeFile() {

        try {
            if (fileMB.getFileEntity() != null) {
                component.getFiles().remove(fileMB.getFileEntity());
                componentService.update(component);
                fileService.delete(fileMB.getFileEntity());
                super.setDBMessage(Component.class.getSimpleName(), "isupdated");
            }
            reinit();
        } catch (Exception e) {
        } finally {
            reinit();
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="GET, SET">
    public List<Gauge> getGauges() {
        return gauges;
    }

    public void setGauges(List<Gauge> gauges) {
        this.gauges = gauges;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public List<FileEntity> getImages() {
//        return fileService.findByUidANDrelatedUID(FileType.IMAGE.toString(), this.component.getUid());
        return images;
    }

    public void setImages(List<FileEntity> images) {
        this.images = images;
    }

    public List<FileEntity> getDocs() {
//        return fileService.findByUidANDrelatedUID(FileType.DOCUMENT.toString(), this.component.getUid());
        return docs;
    }

    public void setDocs(List<FileEntity> docs) {
        this.docs = docs;
    }

    public String getSelectedComponentReference() {
        return selectedComponentReference;
    }

    public void setSelectedComponentReference(String selectedComponentReference) {
        this.selectedComponentReference = selectedComponentReference;
    }

    public String getSelectedIssueId() {
        return selectedIssueId;
    }

    public void setSelectedIssueId(String selectedIssueId) {
        this.selectedIssueId = selectedIssueId;
    }

    //</editor-fold>
    public String getSelectedGaugeReference() {
        return selectedGaugeReference;
    }

    public void setSelectedGaugeReference(String selectedGaugeReference) {
        this.selectedGaugeReference = selectedGaugeReference;
    }
}
