/*


 */
package qmsjee.services.commons.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import qmsjee.entities.common.BaseEntity;
import qmsjee.view.controlers.UserMBean;

/**
 *
 * @author Tomek
 * @param <T>
 * @param <ID>
 */
public abstract class GenericService<T extends BaseEntity, ID extends Serializable> implements IGenericService<T, ID> {

    @PersistenceContext(unitName = "qmsdbPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    @Inject
    UserMBean userMB;
    private Class<T> persistentClass;

    public GenericService() {
    }

    public GenericService(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @Override
    public T save(T entity) {
        try {
            utx.begin();
            entity.setUid(UUID.randomUUID().toString());
            this.em.persist(entity);
            em.flush();
            utx.commit();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} saved to DB", entity);
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(GenericService.class.getName()).log(Level.SEVERE, null, ex);
            }
            Logger.getLogger(GenericService.class.getName()).log(Level.WARNING, "Object {0} are NOT saved to DB", entity);
        }
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T update(T entity) {
        try {
            utx.begin();
            if (entity.getUid() == null || entity.getUid().isEmpty()) {
                entity.setUid(UUID.randomUUID().toString());
            }
            this.em.merge(entity);
            em.flush();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} merged to DB", entity);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                utx.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(GenericService.class.getName()).log(Level.WARNING, "Object {0} are NOT merged in DB", entity);
            }
        }

        return (T) entity;
    }

    @Override
    public void delete(T entity) {
        try {
            utx.begin();
            Object ref = this.em.getReference(persistentClass, entity.getId());
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} deleted from DB", entity);
            this.em.remove(ref);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            Logger.getLogger(GenericService.class.getName()).log(Level.SEVERE, "Cannot delete entity id={0} from DB", entity.getId());
        }
    }

    @Override
    public boolean delete(ID id) {
        try {
            utx.begin();
            Object ref = this.em.getReference(persistentClass, id);
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object id={0} deleted from DB", id);
            this.em.remove(ref);
            utx.commit();
            return true;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            Logger.getLogger(GenericService.class.getName()).log(Level.SEVERE, "Cannot delete entity id={0} from DB", id);
        }
        return false;
    }

    @Override
    public void active(T entity) {
        try {
            utx.begin();
            entity.setActive(true);
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} activated", entity);
            this.getEm().persist(entity);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
    }

    @Override
    public void disactive(T entity) {
        try {
            utx.begin();
            entity.setActive(false);
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} disactivated", entity);
            this.getEm().persist(entity);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
    }

    @Override
    public T findByID(ID id) {
        T result = null;
        try {
            utx.begin();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object " + this.persistentClass.getSimpleName() + " recieved from DB - id {0}", id);
            result = (T) this.em.find(persistentClass, id);
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        List<T> results = new ArrayList<>();
        try {
            utx.begin();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "All collection of {0} recieved from DB", persistentClass.getSimpleName());
            results = this.em.createQuery("select e from " + getPersistentClass().getSimpleName() + " e").getResultList();
            utx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return results;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void flush() {
        em.flush();
        Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Transaction flushed");
    }

    @Override
    public T findByUid(String uid) {
        T result = null;
        try {
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Entity with uid {0} recieved", uid);
            result = (T) em.createNamedQuery(persistentClass.getSimpleName() + ".findByUid")
                    .setParameter("uid", uid)
                    .getSingleResult();
        } catch (Exception ex) {

        }
        return result;
    }

    @Override
    public Number countEntities() {
        Number result = null;
        try {
            Query query = em.createQuery("SELECT COUNT(e) FROM " + persistentClass.getSimpleName() + " e");
            result = (Number) query.getSingleResult();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "System count {0} entities of " + persistentClass.getSimpleName(), result.intValue());
        } catch (Exception e) {
        }

        return result;
    }

    protected EntityManager getEm() {
        return em;
    }

    protected void setEm(EntityManager em) {
        this.em = em;
    }

    protected UserTransaction getUtx() {
        return utx;
    }

    protected void setUtx(UserTransaction utx) {
        this.utx = utx;
    }

    protected UserMBean getUserMB() {
        return userMB;
    }

    protected void setUserMB(UserMBean userMB) {
        this.userMB = userMB;
    }

    protected String modifyQueryCriteria(String criteria) {
        if (criteria.isEmpty()) {
            return "%";
        }
        return criteria.replaceAll("\\*", "%");
    }
}
