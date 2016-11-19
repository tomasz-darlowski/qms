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
import qmsjee.entities.entity.Gauge;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IGaugeService;

/**
 *
 * @author Tomek
 */
public class GaugeService extends GenericService<Gauge, Long> implements IGaugeService, Serializable {

    private static final long serialVersionUID = 2406510115643217962L;

    public GaugeService() {
        super(Gauge.class);
    }

    @Override
    public Gauge findByreferenceNumber(String referenceNumber) {
        Gauge result = null;
        try {
            super.getUtx().begin();
            TypedQuery<Gauge> query = super.getEm().createQuery("SELECT g FROM Gauge g WHERE g.referenceNumber = :referenceNumber", Gauge.class);
            //funkcja if --> (cond)?(true):(false)
            result = (query.setParameter("referenceNumber", referenceNumber).getResultList().size() == 1) ? ((Gauge) query.setParameter("referenceNumber", referenceNumber).getSingleResult()) : null;
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", referenceNumber);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;

    }

    @Override
    public List<Gauge> findBySearchCriteria(String referenceNumber) {
        List<Gauge> results = null;
        try {
            super.getUtx().begin();
            TypedQuery<Gauge> query = super.getEm().createQuery("SELECT g FROM Gauge g WHERE g.referenceNumber LIKE :referenceNumber", Gauge.class);
            results = query.setParameter("referenceNumber", modifyQueryCriteria(referenceNumber)).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", referenceNumber);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return results;
    }

    public List<Gauge> findByComponent(Component comp) {
        List<Gauge> results = null;
        try {
            super.getUtx().begin();
            TypedQuery<Gauge> query = super.getEm().createQuery("SELECT g FROM Gauge g WHERE :comp MEMBER OF g.relatedComponents", Gauge.class);
            results = query.setParameter("comp", comp).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", comp.getReferenceNumber());
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return results;
    }

}
