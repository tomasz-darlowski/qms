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
import qmsjee.entities.entity.Customer;
import qmsjee.services.entityServices.interfaces.ICustomerService;

/**
 *
 * @author Tomek
 */
@FacesValidator("CheckIfCustomerNameExist")
public class CheckIfCustomerNameExist implements Validator {

    @Inject
    private ICustomerService customerService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<Customer> allCustomers = customerService.findAll();
        Long id = (Long) component.getAttributes().get("CustomerID");
        Customer c = customerService.findByID(id);
        int counter = 0;
        if (c == null) {
            for (Customer customer : allCustomers) {
                if (customer.getName().equals(value.toString())) {
                    FacesMessage msg = new FacesMessage("Customer name already exist.");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);
                }
            }
        } else {
            for (Customer customer : allCustomers) {
                if (customer.getName().equals(value.toString())) {
                    counter++;
                }
            }
            if (counter > 1) {
                FacesMessage msg = new FacesMessage("Customer name already exist.");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }

    }
}
