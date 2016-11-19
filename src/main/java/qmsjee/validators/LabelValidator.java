/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.validators;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import qmsjee.entities.entity.Label;
import qmsjee.services.entityServices.interfaces.ILabelService;

/**
 *
 * @author darlotom
 */
@FacesValidator("CheckIfLabelExist")
public class LabelValidator implements Validator {

    @Inject
    ILabelService labelService;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String strLabel = o.toString();
        if (null != strLabel && !strLabel.isEmpty()) {
            List<Label> allLabels = labelService.findAll();
            for (Label object : allLabels) {
                if (object.getLabel().equals(strLabel)) {
                    FacesMessage msg = new FacesMessage("Label already exist");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
        }
    }

}
