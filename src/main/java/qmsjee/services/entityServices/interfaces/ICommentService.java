/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */

package qmsjee.services.entityServices.interfaces;

import java.util.List;
import qmsjee.entities.entity.Comment;
import qmsjee.entities.entity.Item;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author darlotom
 */
public interface ICommentService extends IGenericService<Comment, Long> {
    
    public List<Comment> findAllByItem(Long itemID);
    
}
