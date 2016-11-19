/*
 
 
 */
package qmsjee.services.entityServices.interfaces;

import java.util.List;
import qmsjee.entities.entity.Gauge;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IGaugeService extends IGenericService<Gauge, Long> {

    public Gauge findByreferenceNumber(String referenceNumber);

    public List<Gauge> findBySearchCriteria(String refNumber);
}
