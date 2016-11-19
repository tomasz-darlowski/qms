/*
 
 
 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.Component;
import qmsjee.services.entityServices.interfaces.IComponentService;

/**
 *
 * @author Tomek
 */
@FacesConverter("componentConverter")
public class componentConverter implements Converter {

    @Inject
    IComponentService componentService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            return (Component) componentService.findByreferenceNumber(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Component) value).getReferenceNumber();
    }
}
