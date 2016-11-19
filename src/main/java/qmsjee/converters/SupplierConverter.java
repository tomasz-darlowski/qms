/*


 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.Supplier;
import qmsjee.services.entityServices.interfaces.ISupplierService;

/**
 *
 * @author Tomek
 */
@FacesConverter("SupplierConverter")
public class SupplierConverter implements Converter {

    @Inject
    ISupplierService supplierService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.isEmpty() && !value.equals("-- Select --") && !value.equals("-- Wybierz --")) {
            return supplierService.findByID(Long.parseLong(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Supplier) value).getId().toString();
    }
}
