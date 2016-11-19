/*
 
 
 */
package qmsjee.services.entityServices.interfaces;

import java.util.Date;
import java.util.List;
import qmsjee.entities.entity.Event;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IEventService extends IGenericService<Event, Long> {

    /**
     * Queries DB for all entities from period seted by params
     *
     * @param from min date
     * @param to max date
     * @return set of entities according params
     */
    List<Event> findByPeriod(Date from, Date to);
}
