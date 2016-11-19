/*
 
 
 */
package qmsjee.services.commons.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Tomek
 * @param <T>
 * @param <ID>
 */
public interface IGenericService<T, ID extends Serializable> {

    T save(T entity);

    T update(T entity);

    void delete(T entity);
    
    boolean delete(ID id);

    void active(T entity);

    void disactive(T entity);

    T findByID(ID id);

    T findByUid(String uid);

    List<T> findAll();

    Number countEntities();

    void flush();
}
