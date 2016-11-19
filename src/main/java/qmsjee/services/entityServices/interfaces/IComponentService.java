/*
 
 
 */
package qmsjee.services.entityServices.interfaces;

import java.util.List;
import qmsjee.entities.entity.Component;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IComponentService extends IGenericService<Component, Long> {

    public Component findByreferenceNumber(String referenceNumber);

    public List<Component> findBySearchCriteria(String name, String referenceNumber);
}
