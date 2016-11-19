/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.services.entityServices.interfaces;

import qmsjee.entities.entity.Issue;
import qmsjee.entities.entity.Item;
import qmsjee.services.commons.dao.IGenericService;

/**
 *
 * @author Tomek
 */
public interface IIssueService extends IGenericService<Issue, Long> {

    public Issue findByIssueKey(int key);
}
