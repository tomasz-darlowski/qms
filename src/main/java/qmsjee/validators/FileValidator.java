/*
 
 
 */
package qmsjee.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author darlotom
 */
@FacesValidator("fileValidator")
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        List<FacesMessage> msgs = new ArrayList<>();
        Part file = (Part) value;
        if (file.getSize() == 0) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("nofileselected"), bundle.getString("Select file up to")));
        }
        if (file.getSize() > 2048000) {
            msgs.add(new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("fileistoobig"), bundle.getString("maxsizeis")+" 2MB"));
        }
        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }
}
