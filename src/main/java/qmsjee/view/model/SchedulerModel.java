/*
 
 
 */
package qmsjee.view.model;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import qmsjee.entities.entity.Event;

/**
 *
 * @author Tomek
 */
@Named
@SessionScoped
public class SchedulerModel implements Serializable {

    private static final long serialVersionUID = 8138576496150282108L;
    private Event event;
    private ScheduleModel lazyModel;
    private ScheduleEvent scheduleEvent;
    private boolean viewButtonVisible, editButtonVisible, createButtonVisible;

    @PostConstruct
    public void init() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ScheduleModel getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(ScheduleModel lazyModel) {
        this.lazyModel = lazyModel;
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    public boolean isViewButtonVisible() {
        return viewButtonVisible;
    }

    public void setViewButtonVisible(boolean viewButtonVisible) {
        this.viewButtonVisible = viewButtonVisible;
    }

    public boolean isEditButtonVisible() {
        return editButtonVisible;
    }

    public void setEditButtonVisible(boolean editButtonVisible) {
        this.editButtonVisible = editButtonVisible;
    }

    public boolean isCreateButtonVisible() {
        return createButtonVisible;
    }

    public void setCreateButtonVisible(boolean createButtonVisible) {
        this.createButtonVisible = createButtonVisible;
    }
}
