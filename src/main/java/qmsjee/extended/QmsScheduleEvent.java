/*
 
 
 */
package qmsjee.extended;

import java.util.Date;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author Tomek
 */
public class QmsScheduleEvent extends DefaultScheduleEvent {

    public QmsScheduleEvent() {
    }

    public QmsScheduleEvent(String title, Date start, Date end, Object obj, boolean editable, boolean allDay, String style) {
        super(title, start, end, obj);
        super.setEditable(editable);
        super.setAllDay(allDay);
        super.setStyleClass(style);
    }
    //<editor-fold defaultstate="collapsed" desc="Setters and getters">
    //</editor-fold>
}
