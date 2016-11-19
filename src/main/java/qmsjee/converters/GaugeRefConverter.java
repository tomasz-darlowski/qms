/*
 
 
 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.Gauge;
import qmsjee.services.entityServices.interfaces.IGaugeService;

/**
 *
 * @author Tomek
 */
@FacesConverter("gauageRefConverter")
public class GaugeRefConverter implements Converter {

    @Inject
    IGaugeService gauageService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            return (Gauge) gauageService.findByreferenceNumber(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Gauge) value).getReferenceNumber();
    }
}
