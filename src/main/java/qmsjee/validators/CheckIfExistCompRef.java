/*
 
 
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
import qmsjee.entities.entity.Component;
import qmsjee.services.entityServices.interfaces.IComponentService;

/**
 *
 * @author Tomek
 */
@FacesValidator("CheckIfExistCompRef")
public class CheckIfExistCompRef implements Validator {

    @Inject
    IComponentService componentService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String referenceNumber = value.toString();
        List<Component> components = componentService.findAll();
        Component c = componentService.findByreferenceNumber(referenceNumber);
        if (c != null) {
            FacesMessage msg = new FacesMessage("Reference number already exist.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
