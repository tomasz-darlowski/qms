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
import qmsjee.entities.entity.Gauge;
import qmsjee.services.entityServices.interfaces.IGaugeService;

/**
 *
 * @author darlotom
 */
public class GauageDataModel extends ListDataModel<Gauge> implements SelectableDataModel<Gauge>, Serializable {

    @Inject
    IGaugeService gauageService;

    public GauageDataModel() {
    }

    public GauageDataModel(List<Gauge> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Gauge object) {
        return object.getReferenceNumber();
    }

    @Override
    public Gauge getRowData(String rowKey) {
        return gauageService.findByreferenceNumber(rowKey);
    }

}
