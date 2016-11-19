/*


 */
package qmsjee.services.buisness.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import qmsjee.entities.entity.Event;
import qmsjee.enums.EventStyle;
import qmsjee.services.buisness.IEventValidationService;

/**
 *
 * @author Tomek
 */
@Named
public class EventValidationService implements IEventValidationService, Serializable {

    private static final long serialVersionUID = 193341019880114008L;

    @Override
    public boolean checkDateForCreationOrEdit(Date date) {
        if (date.after(new Date())) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean checkEventIsEditable(Event event, Date date) {
        if (date.after(new Date())) {
            if (event.getStyleClass().equals(EventStyle.GAUAGE.toString())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String styleClassController(Date startDate, String styleClass) {
        if (startDate.getTime() < new Date().getTime() && (!styleClass.equalsIgnoreCase(EventStyle.GAUAGE.toString()))) {
            if (styleClass.equalsIgnoreCase(EventStyle.LOW.toString())) {
                return EventStyle.OLDLOW.toString();
            }
            if (styleClass.equalsIgnoreCase(EventStyle.MEDIUM.toString())) {
                return EventStyle.OLDMEDIUM.toString();
            }
            if (styleClass.equalsIgnoreCase(EventStyle.HIGH.toString())) {
                return EventStyle.OLDHIGH.toString();
            }
        }
        return styleClass;
    }

    @Override
    public boolean editController(Date startDate, boolean edit) {
        if (startDate.getTime() < new Date().getTime()) {
            return false;
        }
        return true;
    }

    @Override
    public void datesValidator(ComponentSystemEvent event) {
        UIComponent source = event.getComponent();
        UIInput from = (UIInput) source.findComponent("from");
        UIInput to = (UIInput) source.findComponent("to");

        Date endDate = (Date) to.getLocalValue();
        Date startDate = (Date) from.getLocalValue();

        boolean flag = false;

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");

        if (startDate == null || endDate == null) {
            context.renderResponse();
        } else {
            if (startDate.before(new Date())) {
                String msg = bundle.getString("eventInThePast");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
                context.addMessage(from.getClientId(), message);
                from.setValid(false);
                flag = true;
            }
            if (endDate.before(new Date())) {
                String msg = bundle.getString("endDateInPast");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
                context.addMessage(to.getClientId(), message);
                to.setValid(false);
                flag = true;
            }
            if (endDate.before(startDate)) {
                String msg = bundle.getString("endDateAfterStartDate");
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
                context.addMessage(to.getClientId(), message);
                to.setValid(false);
                flag = true;
            }
        }
        if (flag) {
            context.renderResponse();
        }
    }
}
