/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
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
import qmsjee.entities.entity.Comment;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.ICommentService;

/**
 *
 * @author darlotom
 */
public class CommentService extends GenericService<Comment, Long> implements ICommentService, Serializable {

    private static final long serialVersionUID = 1L;

    public CommentService() {
        super(Comment.class);
    }

    @Override
    public List<Comment> findAllByItem(Long itemID) {
        List<Comment> results = null;
        try {
            super.getUtx().begin();
            TypedQuery<Comment> query = super.getEm().createQuery("SELECT c FROM Comment c WHERE c.item.id = :itemID", Comment.class);
            results = query.setParameter("itemID", itemID).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object Comment id={0} recieved from DB", itemID.toString());
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return results;
    }
}
