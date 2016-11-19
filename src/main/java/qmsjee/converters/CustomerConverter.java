/*


 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.Customer;
import qmsjee.services.entityServices.interfaces.ICustomerService;

/**
 *
 * @author Tomek
 */
@FacesConverter("CustomerConverter")
public class CustomerConverter implements Converter {

    @Inject
    ICustomerService customerService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.isEmpty() && !value.equals("-- Select --") && !value.equals("-- Wybierz --")) {
            return customerService.findByID(Long.parseLong(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Customer) value).getId().toString();
    }
}
