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
import qmsjee.entities.entity.FileEntity;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IFileService;

/**
 *
 * @author Tomek
 */
public class FileService extends GenericService<FileEntity, Long> implements IFileService, Serializable {

    private static final long serialVersionUID = -3506171842336528585L;

    public FileService() {
        super(FileEntity.class);
    }

    @Override
    public List<FileEntity> findByTypeANDrelatedUID(String type, String relatedUID) {
        List<FileEntity> result = null;
        try {
            super.getUtx().begin();
            TypedQuery<FileEntity> query = super.getEm().createNamedQuery("FileEntity.findByTypeANDrelatedUID", FileEntity.class);
            result = query.setParameter("docType", type).setParameter("relatedUID", relatedUID).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", type);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    @Override
    public List<FileEntity> findBySearchCriteriaWithItem(String fileName, String relatedUID, String docType) {
        List<FileEntity> result = null;
        try {
            super.getUtx().begin();
            TypedQuery<FileEntity> query = super.getEm().createNamedQuery("FileEntity.findBySearchCriteriaWithItem", FileEntity.class);
            result = query.setParameter("fileName", modifyQueryCriteria(fileName)).setParameter("relatedUID", relatedUID).setParameter("docType", docType).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", fileName);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;

    }

    @Override
    public List<FileEntity> findBySearchCriteria(String fileName, String docType) {
        List<FileEntity> result = null;
        try {
            super.getUtx().begin();
            TypedQuery<FileEntity> query = super.getEm().createNamedQuery("FileEntity.findBySearchCriteria", FileEntity.class);
            result = query.setParameter("fileName", modifyQueryCriteria(fileName)).setParameter("docType", docType).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", fileName);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }
}
