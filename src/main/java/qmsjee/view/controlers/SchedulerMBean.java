/*


 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import qmsjee.entities.entity.Event;
import qmsjee.enums.Navigation;
import qmsjee.extended.QmsScheduleEvent;
import qmsjee.services.EmailService;
import qmsjee.services.buisness.IEventValidationService;
import qmsjee.services.entityServices.interfaces.IEventService;
import qmsjee.view.controlers.common.CommonMBean;
import qmsjee.view.model.SchedulerModel;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class SchedulerMBean  extends CommonMBean implements Serializable {

    private static final long serialVersionUID = -2325039702834061444L;
    @Inject
    IEventService eventService;
    @Inject
    EmailService emailService;
    @Inject
    IEventValidationService eventValidationService;
    @Inject
    SchedulerModel schedulerModel;

    public SchedulerMBean() {
    }

    @PostConstruct
    public void init() {
        schedulerModel.setLazyModel(new LazyScheduleModel() {
            private static final long serialVersionUID = 1297279688835547588L;

            @Override
            public void loadEvents(Date start, Date end) {
                this.clear();
                Calendar cal = Calendar.getInstance();
                TimeZone tz = cal.getTimeZone();
                long msFromEpochGmt = start.getTime();
                int offsetFromUTC = tz.getOffset(msFromEpochGmt);
                Date f = new Date(start.getTime() + (offsetFromUTC));
                Date t = new Date(end.getTime() + (offsetFromUTC));
                List<Event> lista = eventService.findByPeriod(f, t);
                if (!lista.isEmpty()) {
                    for (Event ev : lista) {
                        schedulerModel.setScheduleEvent(new QmsScheduleEvent(ev.getTitle(),
                                                                             ev.getDateFrom(),
                                                                             ev.getDateTo(),
                                                                             ev,
                                                                             eventValidationService.editController(ev.getDateFrom(),ev.isEditable()), 
                                                                             ev.isAllDay(),
                                                                             eventValidationService.styleClassController(ev.getDateFrom(),
                                                                                                                         ev.getStyleClass())));
                        addEvent(schedulerModel.getScheduleEvent());
                    }
                }

            }
        });
    }

    public String goBack() {
        return Navigation.SCHEDULER.toString();
    }

    public String persistEvent() {
        eventService.save(schedulerModel.getEvent());
        schedulerModel.setEvent(new Event());
        FacesContext context = FacesContext.getCurrentInstance();
        super.setDBMessage(Event.class.getSimpleName(), "isadded");
        return Navigation.SCHEDULER.toString();
    }

    public String addNewEvent() {
        schedulerModel.setEvent(new Event());
        return Navigation.CREATE.toString();
    }

    public String updateEvent() throws Exception {
        eventService.update(schedulerModel.getEvent());
        emailService.resendInvitations(schedulerModel.getEvent());
        FacesContext context = FacesContext.getCurrentInstance();
        super.setDBMessage(Event.class.getSimpleName(), "isupdated");
        return Navigation.SCHEDULER.toString();
    }

    public String save() throws Exception {
        eventService.update(schedulerModel.getEvent());
        emailService.resendInvitations(schedulerModel.getEvent());
        FacesContext context = FacesContext.getCurrentInstance();
        super.setDBMessage(Event.class.getSimpleName(), "isupdated");
        return Navigation.VIEW.toString();
    }

    public String deleteEvent() {
        eventService.delete(schedulerModel.getEvent());
        FacesContext context = FacesContext.getCurrentInstance();
        return Navigation.SCHEDULER.toString();
    }

    public void onEventSelect(SelectEvent e) {
        schedulerModel.setScheduleEvent((ScheduleEvent) e.getObject());
        schedulerModel.setEvent((Event) schedulerModel.getScheduleEvent().getData());
        if (eventValidationService.checkEventIsEditable(schedulerModel.getEvent(), schedulerModel.getScheduleEvent().getStartDate())) {
            RequestContext.getCurrentInstance().addCallbackParam("showDialog", true);
            schedulerModel.setEditButtonVisible(true);
            schedulerModel.setCreateButtonVisible(false);
        } else {
            RequestContext.getCurrentInstance().addCallbackParam("showDialog", false);
            schedulerModel.setEditButtonVisible(false);
            schedulerModel.setCreateButtonVisible(false);
        }
        schedulerModel.setViewButtonVisible(true);
    }

    public void onDateSelect(SelectEvent e) {
        Date initDate = (Date) e.getObject();
        if (eventValidationService.checkDateForCreationOrEdit(initDate)) {
            RequestContext.getCurrentInstance().addCallbackParam("showDialog", true);
            schedulerModel.setViewButtonVisible(false);
            schedulerModel.setCreateButtonVisible(true);
            schedulerModel.setEditButtonVisible(false);
            long longInitDate = initDate.getTime();
            longInitDate += 60 * 60 * 1000;
            schedulerModel.setEvent(new Event("", "", initDate, new Date(longInitDate), ""));
        } else {
            RequestContext.getCurrentInstance().addCallbackParam("showDialog", false);
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("cantAddEventInThePast"), ""));
        }
    }

    public String createAction() {
        return Navigation.CREATE.toString();
    }

    public String editAction() {
        return Navigation.EDIT.toString();
    }

    public SchedulerModel getSchedulerModel() {
        return schedulerModel;
    }

    public void setSchedulerModel(SchedulerModel schedulerModel) {
        this.schedulerModel = schedulerModel;
    }

    public IEventValidationService getEventValidationService() {
        return eventValidationService;
    }

    public void setEventValidationService(IEventValidationService eventValidationService) {
        this.eventValidationService = eventValidationService;
    }
}
