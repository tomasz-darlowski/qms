/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import qmsjee.entities.entity.Label;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.ILabelService;

/**
 *
 * @author Tomek
 */
public class LabelService extends GenericService<Label, Long> implements ILabelService, Serializable {

    public LabelService() {
        super(Label.class);
    }
}
