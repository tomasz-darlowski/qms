/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import qmsjee.entities.entity.Item;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IItemService;

/**
 *
 * @author Tomek
 */
public class ItemService extends GenericService<Item, Long> implements IItemService, Serializable {

    private static final long serialVersionUID = 2406510115643217962L;

    public ItemService() {
        super(Item.class);
    }

    @Override
    public Item findByreferenceNumber(String referenceNumber) {
        Item result = null;
        try {
            super.getUtx().begin();
            TypedQuery<Item> query = super.getEm().createQuery("SELECT i FROM Item i WHERE i.referenceNumber = :referenceNumber", Item.class);
            result = (query.setParameter("referenceNumber", referenceNumber).getResultList().size() > 0) ? ((Item) query.setParameter("referenceNumber", referenceNumber).getSingleResult()) : null;
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Object {0} recieved from DB", referenceNumber);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;

    }
}
