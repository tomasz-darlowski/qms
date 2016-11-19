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
import qmsjee.entities.entity.Component;
import qmsjee.services.entityServices.interfaces.IComponentService;

/**
 *
 * @author darlotom
 */
public class ComponentDataModel extends ListDataModel<Component> implements SelectableDataModel<Component>, Serializable {

    @Inject
    IComponentService componentService;

    public ComponentDataModel() {
    }

    public ComponentDataModel(List<Component> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Component object) {
        return object.getReferenceNumber();
    }

    @Override
    public Component getRowData(String rowKey) {
        return componentService.findByreferenceNumber(rowKey);
    }

}
