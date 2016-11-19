/*
 
 
 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import qmsjee.entities.entity.Component;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IComponentService;

/**
 *
 * @author Tomek
 */
public class ComponentService extends GenericService<Component, Long> implements IComponentService, Serializable {

    private static final long serialVersionUID = 7606559513864080055L;

    public ComponentService() {
        super(Component.class);
    }

    @Override
    public Component findByreferenceNumber(String referenceNumber) {
        Component result = null;
        try {
            super.getUtx().begin();
            TypedQuery<Component> query = super.getEm().createQuery("SELECT c FROM Component c WHERE c.referenceNumber = :referenceNumber", Component.class);
            result = (query.setParameter("referenceNumber", referenceNumber).getResultList().size() == 1) ? ((Component) query.setParameter("referenceNumber", referenceNumber).getSingleResult()) : null;
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", referenceNumber);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;

    }

    @Override
    public List<Component> findBySearchCriteria(String name, String referenceNumber) {
        List<Component> results;
        TypedQuery<Component> query = super.getEm().createNamedQuery("Component.findBySearchCriteria", Component.class);
        results = query.setParameter("name", modifyQueryCriteria(name)).setParameter("referenceNumber", modifyQueryCriteria(referenceNumber)).getResultList();
        return results;
    }

}
