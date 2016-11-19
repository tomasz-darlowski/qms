/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import qmsjee.entities.entity.Event;
import qmsjee.entities.entity.Issue;
import qmsjee.enums.IssueStatus;
import qmsjee.enums.IssueType;
import qmsjee.services.entityServices.impl.EventService;
import qmsjee.services.entityServices.impl.IssueService;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class DashboardMBean implements Serializable {

    private CartesianChartModel priorityChartModel;
    private CartesianChartModel statusChartModel;
    private CartesianChartModel typeChartModel;
    private List<Issue> recentIssues;
    private List<Event> upcomingEvents;

    private ResourceBundle bundle;
    @Inject
    IssueService issueService;
    @Inject
    EventService eventService;

    private String totalIssues;
    private String totalIssuesByStatus;
    private String totalIssuesByType;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, "msg");
        createPriorityModel();
        createStatusModel();
        createTypeModel();
        totalIssues = issueService.countEntities().toString();
        recentIssues = issueService.findRecent(5);
        upcomingEvents = eventService.upcomingEvents(5);
    }

    private void createPriorityModel() {
        priorityChartModel = new CartesianChartModel();

        ChartSeries priorityTotal = new ChartSeries();
        priorityTotal.setLabel(bundle.getString("total"));

        int a, b, c, d, e, f;
        priorityTotal.set(bundle.getString("trivial"), a = issueService.findByPriority("trivial").size());
        priorityTotal.set(bundle.getString("minor"), b = issueService.findByPriority("minor").size());
        priorityTotal.set(bundle.getString("major"), c = issueService.findByPriority("major").size());
        priorityTotal.set(bundle.getString("critical"), d = issueService.findByPriority("critical").size());
        priorityTotal.set(bundle.getString("blocker"), e = issueService.findByPriority("blocker").size());

        int[] values = {a, b, c, d, e};
        int max = values[0];
        for (int ktr = 0; ktr < values.length; ktr++) {
            if (values[ktr] > max) {
                max = values[ktr];
            }
        }

        ChartSeries priorityOpened = new ChartSeries();
        priorityOpened.setLabel(bundle.getString("open"));

        priorityOpened.set(bundle.getString("trivial"), issueService.findByPriority("trivial", "Open").size());
        priorityOpened.set(bundle.getString("minor"), issueService.findByPriority("minor", "Open").size());
        priorityOpened.set(bundle.getString("major"), issueService.findByPriority("major", "Open").size());
        priorityOpened.set(bundle.getString("critical"), issueService.findByPriority("critical", "Open").size());
        priorityOpened.set(bundle.getString("blocker"), issueService.findByPriority("blocker", "Open").size());

        priorityChartModel.addSeries(priorityTotal);
        priorityChartModel.addSeries(priorityOpened);

    }

    private void createStatusModel() {
        statusChartModel = new CartesianChartModel();

        ChartSeries status = new ChartSeries();
        status.setLabel("Total");

        int a, b, c, d, e, f;
        status.set(bundle.getString("open"), a = issueService.findByStatus(IssueStatus.OPEN.toString()).size());
        status.set(bundle.getString("inprogress"), b = issueService.findByStatus(IssueStatus.INPROGRESS.toString()).size());
        status.set(bundle.getString("rejected"), c = issueService.findByStatus(IssueStatus.REJECTED.toString()).size());
        status.set(bundle.getString("reopen"), d = issueService.findByStatus(IssueStatus.REOPENED.toString()).size());
        status.set(bundle.getString("closed"), e = issueService.findByStatus(IssueStatus.CLOSED.toString()).size());
        status.set(bundle.getString("resolved"), f= issueService.findByStatus(IssueStatus.RESOLVED.toString()).size());

        int[] values = {a, b, c, d, e, f};
        int max = values[0];
        for (int ktr = 0; ktr < values.length; ktr++) {
            if (values[ktr] > max) {
                max = values[ktr];
            }
        }

        statusChartModel.addSeries(status);

    }

    private void createTypeModel() {
        typeChartModel = new CartesianChartModel();

        ChartSeries type = new ChartSeries();
        type.setLabel(bundle.getString("total"));

        int a, b, c;
        type.set(bundle.getString("bug"), a = issueService.findByType(IssueType.BUG.toString()).size());
        type.set(bundle.getString("task"), b = issueService.findByType(IssueType.TASK.toString()).size());
        type.set(bundle.getString("improvement"), c = issueService.findByType(IssueType.IMPOVEMENT.toString()).size());

        ChartSeries typeOpened = new ChartSeries();
        typeOpened.setLabel(bundle.getString("open"));

        typeOpened.set(bundle.getString("bug"), issueService.findByType(IssueType.BUG.toString(), "Open").size());
        typeOpened.set(bundle.getString("task"), issueService.findByType(IssueType.TASK.toString(), "Open").size());
        typeOpened.set(bundle.getString("improvement"), issueService.findByType(IssueType.IMPOVEMENT.toString(), "Open").size());

        int[] values = {a, b, c};
        int max = values[0];
        for (int ktr = 0; ktr < values.length; ktr++) {
            if (values[ktr] > max) {
                max = values[ktr];
            }
        }


        typeChartModel.addSeries(type);
        typeChartModel.addSeries(typeOpened);

    }

    public CartesianChartModel getPriorityChartModel() {
        return priorityChartModel;
    }

    public void setPriorityChartModel(CartesianChartModel priorityChartModel) {
        this.priorityChartModel = priorityChartModel;
    }

    public String getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(String totalIssues) {
        this.totalIssues = totalIssues;
    }

    public CartesianChartModel getStatusChartModel() {
        return statusChartModel;
    }

    public void setStatusChartModel(CartesianChartModel statusChartModel) {
        this.statusChartModel = statusChartModel;
    }

    public CartesianChartModel getTypeChartModel() {
        return typeChartModel;
    }

    public void setTypeChartModel(CartesianChartModel typeChartModel) {
        this.typeChartModel = typeChartModel;
    }

    public List<Issue> getRecentIssues() {
        return recentIssues;
    }

    public void setRecentIssues(List<Issue> recentIssues) {
        this.recentIssues = recentIssues;
    }

    public List<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(List<Event> upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getTotalIssuesByStatus() {
        return totalIssuesByStatus;
    }

    public void setTotalIssuesByStatus(String totalIssuesByStatus) {
        this.totalIssuesByStatus = totalIssuesByStatus;
    }

    public String getTotalIssuesByType() {
        return totalIssuesByType;
    }

    public void setTotalIssuesByType(String totalIssuesByType) {
        this.totalIssuesByType = totalIssuesByType;
    }

}
