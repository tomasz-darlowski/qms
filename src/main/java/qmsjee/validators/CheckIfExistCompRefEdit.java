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
@FacesValidator("CheckIfExistCompRefEdit")
public class CheckIfExistCompRefEdit implements Validator {

    @Inject
    IComponentService componentService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String referenceNumber = value.toString();
        List<Component> components = componentService.findAll();
        String uid = (String) component.getAttributes().get("componentUID");
        for (Component comp : components) {
            if ((comp.getReferenceNumber().equalsIgnoreCase(referenceNumber)) && !(comp.getUid().equals(uid))) {
                FacesMessage msg = new FacesMessage("Reference number already exist.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
