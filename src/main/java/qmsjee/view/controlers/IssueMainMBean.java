/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.AppUser;
import qmsjee.entities.entity.Issue;
import qmsjee.entities.entity.Label;
import qmsjee.services.entityServices.impl.IssueService;
import qmsjee.utils.ValidationUtils;
import qmsjee.view.model.IssueDataModel;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class IssueMainMBean implements Serializable {

    @Inject
    private IssueService issueService;
    private IssueDataModel issueDataModel;
    private Issue selectedIssue;
    private List<Issue> issues;
    private List<Issue> issueTempList;

    //SEARCH FIELDS
    private String title;
    private String issueType;
    private List<String> issueStatus;
    private List<String> issuePriority;
    private AppUser assigneTo;
    private AppUser createBy;
    private Date createAfer;
    private Date createBefore;
    private Label label;

    @PostConstruct
    public void init() {
    }

    public void resetSearchValues() {
        title = "";
        issueType = "";
        issueStatus = new ArrayList<>();
        issuePriority = new ArrayList<>();
        assigneTo = null;
        createBy = null;
        createAfer = null;
        createBefore = null;
        label = null;
    }

    public void search() {
        if (issues != null) {
            issues.clear();
        }
        if (issueType.equals("any")) {
            issues = issueService.findAll();
        } else {
            issues = issueService.findByType(issueType);
        }
        issueTempList = new ArrayList<>();
        for (Issue issue : issues) {
            if (!ValidationUtils.checkDateIsBetween(createAfer, createBefore, issue.getCreateDate())) {
                issueTempList.add(issue);
                continue;
            }
            if (label != null && !issue.getLabels().contains(label)) {
                issueTempList.add(issue);
                continue;
            }
            if (!issuePriority.isEmpty() && !issuePriority.contains(issue.getIssuePriority())) {
                issueTempList.add(issue);
                continue;
            }
            if (!issueStatus.isEmpty() && !issueStatus.contains(issue.getIssueStatus())) {
                issueTempList.add(issue);
                continue;
            }
            if (!title.isEmpty() && !issue.getTitle().contains(title)) {
                issueTempList.add(issue);
                continue;
            }
            if (assigneTo != null && !issue.getAssigneTo().equals(assigneTo)) {
                issueTempList.add(issue);
                continue;
            }
            if (createBy != null && !issue.getCreateBy().equals(createBy)) {
                issueTempList.add(issue);
                continue;
            }
        }
        issues.removeAll(issueTempList);

        issueDataModel = new IssueDataModel(issues);
    }

    //<editor-fold defaultstate="collapsed" desc="Setters and getters">
    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public AppUser getCreateBy() {
        return createBy;
    }

    public void setCreateBy(AppUser createBy) {
        this.createBy = createBy;
    }

    public AppUser getAssigneTo() {
        return assigneTo;
    }

    public void setAssigneTo(AppUser assigneTo) {
        this.assigneTo = assigneTo;
    }

    public IssueDataModel getIssueDataModel() {
        return issueDataModel;
    }

    public void setIssueDataModel(IssueDataModel issueDataModel) {
        this.issueDataModel = issueDataModel;
    }

    public Issue getSelectedIssue() {
        return selectedIssue;
    }

    public void setSelectedIssue(Issue selectedIssue) {
        this.selectedIssue = selectedIssue;
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

    public List<String> getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(List<String> issueStatus) {
        this.issueStatus = issueStatus;
    }

    public List<String> getIssuePriority() {
        return issuePriority;
    }

    public void setIssuePriority(List<String> issuePriority) {
        this.issuePriority = issuePriority;
    }

    public Date getCreateAfer() {
        return createAfer;
    }

    public void setCreateAfer(Date createAfer) {
        this.createAfer = createAfer;
    }

    public Date getCreateBefore() {
        return createBefore;
    }

    public void setCreateBefore(Date createBefore) {
        this.createBefore = createBefore;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
//</editor-fold>

}
