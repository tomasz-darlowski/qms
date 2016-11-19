/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import qmsjee.entities.entity.Issue;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.IIssueService;

/**
 *
 * @author Tomek
 */
public class IssueService extends GenericService<Issue, Long> implements IIssueService, Serializable {

    public IssueService() {
        super(Issue.class);
    }

    @Override
    public Issue findByIssueKey(int key) {
        Issue result = null;
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.edvaNumber = :edvaNumber ORDER BY e.issueNumber DESC", Issue.class);
            result = query.setParameter("edvaNumber", key).getSingleResult();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Issue {0} recieved from DB", key);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    @Override
    synchronized public Issue save(Issue entity) {
        int count = 1;
        Query q = super.getEm().createQuery("SELECT COUNT(i) FROM Issue i");
        Number n = (Number) q.getSingleResult();
        count += n.intValue();
        entity.setIssueNumber(count);
        return super.save(entity);
    }

    @Override
    public List<Issue> findAll() {
        List<Issue> results = new ArrayList<>();
        try {
            super.getUtx().begin();
            results = super.getEm().createQuery("select c from Issue c ORDER BY c.issueNumber DESC").getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "All collection of Issues recieved from DB");
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return results;
    }

    public List<Issue> findByType(String type) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.issueType = :type ORDER BY c.issueNumber DESC", Issue.class);
            result = query.setParameter("type", type).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of type {0}", type);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    public List<Issue> findByType(String type, String status) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.issueType = :type AND c.issueStatus=:status ORDER BY c.issueNumber DESC", Issue.class);
            result = query.setParameter("type", type).setParameter("status", status).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of type {0}", type);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    public List<Issue> findByPriority(String priority) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.issuePriority = :priority ORDER BY c.issueNumber DESC", Issue.class);
            result = query.setParameter("priority", priority).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of priority {0}", priority);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    public List<Issue> findByPriority(String priority, String status) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.issuePriority = :priority AND c.issueStatus = :status ORDER BY c.issueNumber DESC", Issue.class);
            result = query.setParameter("priority", priority).setParameter("status", status).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of priority {0}", priority);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    public List<Issue> findByStatus(String status) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c WHERE c.issueStatus = :status ORDER BY c.issueNumber DESC", Issue.class);
            result = query.setParameter("status", status).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of status {0}", status);
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

    public List<Issue> findRecent(int quantity) {
        List<Issue> result = new ArrayList<>();
        try {
            super.getUtx().begin();
            TypedQuery<Issue> query = super.getEm().createQuery("SELECT c FROM Issue c ORDER BY c.createDate DESC", Issue.class);
            result = query.setMaxResults(quantity).getResultList();
            Logger.getLogger(GenericService.class.getName()).log(Level.INFO, "Recieved issues of status");
            super.getUtx().commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
        }
        return result;
    }

}
