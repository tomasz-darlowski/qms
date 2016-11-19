/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.view.controlers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.entities.entity.Component;
import qmsjee.entities.entity.FileEntity;
import qmsjee.entities.entity.Issue;
import qmsjee.entities.entity.Label;
import qmsjee.enums.FileType;
import qmsjee.enums.IssueStatus;
import qmsjee.enums.Navigation;
import qmsjee.services.entityServices.interfaces.IComponentService;
import qmsjee.services.entityServices.interfaces.IFileService;
import qmsjee.services.entityServices.interfaces.IIssueService;
import qmsjee.services.entityServices.interfaces.ILabelService;
import qmsjee.view.controlers.common.CommonMBean;
import qmsjee.view.interfaces.IFileAttachable;
import qmsjee.view.interfaces.IPersistable;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class IssueMBean extends CommonMBean implements Serializable, IFileAttachable, IPersistable {

    @Inject
    IIssueService issueServ;
    @Inject
    IComponentService componentService;
    @Inject
    ILabelService labelServ;
    @Inject
    IFileService fileService;
    @Inject
    private UserMBean userMB;
    @Inject
    private FileMBean fileMB;
    private List<FileEntity> images;
    private List<FileEntity> docs;
    private String selectedComponentReference;
    private Issue issue;

    private Issue selectedLinkIssue;
    private String selectedIssueReference;

    private boolean close;
    private boolean reopen;
    private boolean start;
    private boolean reject;
    private boolean resolve;

    //SEARCH FIELDS
    private String title;
    private String issueType;
    private String issueStatus;
    private String issuePriority;
    private AppUser assigneTo;
    private AppUser createBy;
    private Date createAfer;
    private Date createBefore;
    private String label;

    @PostConstruct
    public void init() {
        if (null == issue) {
            issue = new Issue();
            issue.setActive(true);
        }
        reinit();
    }

    public void reinit() {
        if (null != issue.getIssueStatus()) {
            if (issue.getIssueStatus().equals(IssueStatus.OPEN.toString())) {
                close = start = reject = true;
                reopen = resolve = false;
            } else if (issue.getIssueStatus().equals(IssueStatus.CLOSED.toString())) {
                close = start = reject = resolve = false;
                reopen = true;
            } else if (issue.getIssueStatus().equals(IssueStatus.INPROGRESS.toString())) {
                start = reopen = reject = false;
                resolve = close = true;
            } else if (issue.getIssueStatus().equals(IssueStatus.REJECTED.toString())) {
                close = reopen = true;
                start = resolve = reject = false;
            } else if (issue.getIssueStatus().equals(IssueStatus.REOPENED.toString())) {
                close = start = reject = true;
                reopen = resolve = false;
            } else if (issue.getIssueStatus().equals(IssueStatus.RESOLVED.toString())) {
                close = start = reject = true;
                reopen = resolve = false;
            }
        }
        images = fileService.findByTypeANDrelatedUID(FileType.IMAGE.toString(), this.issue.getUid());
        docs = fileService.findByTypeANDrelatedUID(FileType.DOCUMENT.toString(), this.issue.getUid());
    }

    public List<Component> complete(String query) {
        List<Component> results = new ArrayList<>();
        if (!query.isEmpty()) {
            List<Component> temp = componentService.findAll();
            if (!temp.isEmpty()) {
                for (Component item : temp) {
                    if (item.getReferenceNumber().startsWith(query)) {
                        results.add(item);
                    }
                }
            }
        }
        return results;
    }

    public List<Label> completeLabel(String query) {
        List<Label> results = new ArrayList<>();
        if (!query.isEmpty()) {
            List<Label> temp = labelServ.findAll();
            for (Label item : temp) {
                if (item.getLabel().startsWith(query)) {
                    results.add(item);
                }
            }
        }
        return results;
    }

    public List<Issue> completeIssue(String query) {
        List<Issue> results = new ArrayList<>();
        if (!query.isEmpty()) {
            List<Issue> temp = issueServ.findAll();
            for (Issue item : temp) {
                if (issue != item) {
                    if (item.getTitle().contains(query)
                            || ((Long) item.getIssueNumber()).toString().startsWith(query)
                            || item.getId().toString().startsWith(query)
                            || ((Long) item.getIssueNumber()).toString().equals(query)
                            || item.getId().toString().startsWith(query)) {
                        results.add(item);
                    }
                }
            }
        }
        return results;
    }

    public void resetDialogs() {
        selectedLinkIssue = null;
    }

    @Override
    public void uploadDocument() throws IOException {
        fileMB.uploadFile(FileType.DOCUMENT, this.issue);
        setFileEntityData();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
    }

    @Override
    public void uploadImage() throws IOException {
        fileMB.uploadFile(FileType.IMAGE, this.issue);
        setFileEntityData();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
    }

    @Override
    public void uploadReport() throws IOException {
        fileMB.uploadFile(FileType.REPORT, this.issue);
        setFileEntityData();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
    }

    @Override
    public String removeFile() {
        if (fileMB.getFileEntity() != null) {
            issue.getFiles().remove(fileMB.getFileEntity());
            issueServ.update(this.issue);
            fileService.delete(fileMB.getFileEntity());
        }
        reinit();
        return "";
    }

    public void changeStatus(String status) {
        if (status.equals(IssueStatus.RESOLVED.toString())) {
            issue.setResolveDate(new Date());
        }
        issue.setIssueStatus(status);
        issueServ.update(issue);
        issueServ.flush();
        reinit();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
    }

    @Override
    public void setFileEntityData() {
        fileMB.getFileEntity().setRelatedUID(this.issue.getUid());
        fileMB.getFileEntity().setCreateBy(userMB.getUser());
        this.getIssue().getFiles().add(fileMB.getFileEntity());
        issueServ.update(this.getIssue());
        issueServ.flush();
        reinit();
    }

    @Override
    public String createAndClose() {
        prepareData();
        issue.setIssueStatus(IssueStatus.OPEN.toString());
        issueServ.save(this.issue);
        issueServ.flush();
        super.setDBMessage(Issue.class.getSimpleName(), "isadded");
        return "issues";
    }


    @Override
    public String createAndEdit() {
        prepareData();
        issue.setIssueStatus(IssueStatus.OPEN.toString());
        issueServ.save(this.issue);
        issueServ.flush();
         super.setDBMessage(Issue.class.getSimpleName(), "isadded");
        return "edit";
    }

    @Override
    public String saveAndClose() {
        prepareData();
        issueServ.update(issue);
        issueServ.flush();
         super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
        return "issues";
    }

    public String save() {
        prepareData();
        issueServ.update(issue);
        issueServ.flush();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
        return Navigation.VIEW.toString();
    }

    public String saveOnView() {
        prepareData();
        if (selectedLinkIssue != null && selectedLinkIssue != issue && !issue.getLinkedIssues().contains(selectedLinkIssue)) {
            issue.getLinkedIssues().add(selectedLinkIssue);
            selectedLinkIssue = null;
        }
        issueServ.update(issue);
        issueServ.flush();
        super.setDBMessage(Issue.class.getSimpleName(), "isupdated");
        return null;
    }

    @Override
    public String delete() {
        return "issues";
    }

    @Override
    public void prepareData() {
        if (issue.getUid() == null) {
            issue.setCreateDate(new Date());
            issue.setCreateBy(userMB.getUser());
        } else {
            issue.setModifyDate(new Date());
            issue.setModifyBy(userMB.getUser());
        }

    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
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

    public String getSelectedComponentReference() {
        return selectedComponentReference;
    }

    public void setSelectedComponentReference(String selectedComponentReference) {
        this.selectedComponentReference = selectedComponentReference;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    public boolean isReopen() {
        return reopen;
    }

    public void setReopen(boolean reopen) {
        this.reopen = reopen;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

    public boolean isResolve() {
        return resolve;
    }

    public void setResolve(boolean resolve) {
        this.resolve = resolve;
    }

    public Issue getSelectedLinkIssue() {
        return selectedLinkIssue;
    }

    public void setSelectedLinkIssue(Issue selectedLinkIssue) {
        this.selectedLinkIssue = selectedLinkIssue;
    }

    public String getSelectedIssueReference() {
        return selectedIssueReference;
    }

    public void setSelectedIssueReference(String selectedIssueReference) {
        this.selectedIssueReference = selectedIssueReference;
    }

}
