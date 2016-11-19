/*
 
 
 */
package qmsjee.services.buisness;

import java.util.Date;
import javax.faces.event.ComponentSystemEvent;
import qmsjee.entities.entity.Event;

/**
 *
 * @author Tomek
 */
public interface IEventValidationService {
    //Method check if Date param is not in the past.

    public boolean checkDateForCreationOrEdit(Date date);
    //Method check if Event is NOT event from the past and is not gauge event.

    public boolean checkEventIsEditable(Event event, Date date);

    public String styleClassController(Date startDate, String styleClass);

    public boolean editController(Date startDate, boolean edit);

    public void datesValidator(ComponentSystemEvent event);
}
