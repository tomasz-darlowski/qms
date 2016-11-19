/*
 
 
 */
package qmsjee.services.entityServices.interfaces;

import java.util.List;
import qmsjee.entities.entity.FileEntity;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IFileService extends IGenericService<FileEntity, Long> {

    public List<FileEntity> findByTypeANDrelatedUID(String type, String relatedUID);

    public List<FileEntity> findBySearchCriteriaWithItem(String fileName, String relatedUID, String docType);

    public List<FileEntity> findBySearchCriteria(String fileName, String docType);
}
