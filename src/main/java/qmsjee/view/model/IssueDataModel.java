/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.view.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import org.primefaces.model.SelectableDataModel;
import qmsjee.entities.entity.Issue;
import qmsjee.services.entityServices.interfaces.IIssueService;

/**
 *
 * @author Tomek
 */
public class IssueDataModel extends ListDataModel<Issue> implements SelectableDataModel<Issue>, Serializable {

    @Inject
    IIssueService issueService;

    public IssueDataModel(List<Issue> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Issue object) {
        return object.getId();
    }

    @Override
    public Issue getRowData(String rowKey) {
        return issueService.findByID(Long.valueOf(rowKey));
    }

}
