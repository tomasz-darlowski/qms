/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import qmsjee.entities.entity.Gauge;
import qmsjee.services.entityServices.interfaces.IGaugeService;

/**
 *
 * @author Tomek
 */
@FacesValidator("CheckIfGaugeRefExistCreate")
public class CheckIfGaugeRefExist_create implements Validator {

    @Inject
    IGaugeService gauageService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String referenceNumber = value.toString();
        Gauge gaug = gauageService.findByreferenceNumber(referenceNumber);
        if (gaug != null) {
            FacesMessage msg = new FacesMessage("Reference number already exist.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
