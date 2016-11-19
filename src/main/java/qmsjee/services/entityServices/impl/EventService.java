/*


 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import qmsjee.entities.entity.Event;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IEventService;

@Named
public class EventService extends GenericService<Event, Long> implements IEventService, Serializable {

    private static final long serialVersionUID = 237296737447654727L;

    public EventService() {
        super(Event.class);
    }

    @Override
    public List<Event> findByPeriod(Date from, Date to) {
        List<Event> results = new ArrayList<>();
        try {
            super.getUtx().begin();
            //TODO Below hardcoded - should be dynamic
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            long msFromEpochGmt = from.getTime();
            int offsetFromUTC = tz.getOffset(msFromEpochGmt);
            Date f = new Date(from.getTime() + (offsetFromUTC));
            Date t = new Date(to.getTime() + (offsetFromUTC));
            results = (List<Event>) super.getEm().createQuery("SELECT e FROM Event e WHERE (e.dateFrom >= :selectedDateFrom AND e.dateFrom <= :selectedDateTo) OR (e.dateTo <= :selectedDateTo AND e.dateTo >= :selectedDateFrom)").setParameter("selectedDateFrom", f).setParameter("selectedDateTo", t).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Objects of {0} recieved from DB", Event.class.getSimpleName());
            super.getUtx().commit();
        } catch (Exception e) {
        }
        return results;
    }

    public List<Event> upcomingEvents(int quantity) {
        List<Event> results = new ArrayList<>();
        try {
            super.getUtx().begin();
            results = (List<Event>) super.getEm().createQuery("SELECT e FROM Event e WHERE e.dateFrom>:date ORDER BY e.dateFrom ASC").setParameter("date", new Date()).setMaxResults(quantity).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Objects of {0} recieved from DB", Event.class.getSimpleName());
            super.getUtx().commit();
        } catch (Exception e) {
        }
        return results;
    }
}
