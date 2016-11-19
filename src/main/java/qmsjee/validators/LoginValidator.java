/*
 
 
 */
package qmsjee.validators;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@Named
@RequestScoped
public class LoginValidator implements Validator {

    @Inject
    IUserService userService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String userName = (String) value;
        if (userName.length() != 8) {
            FacesMessage msg = new FacesMessage("Username length has to be 8 signs");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
//        boolean result = userService.isUsernameExist(userName);
//        if (result) {
//            FacesMessage msg = new FacesMessage("Username already exist.");
//            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(msg);
//        }

    }
}
