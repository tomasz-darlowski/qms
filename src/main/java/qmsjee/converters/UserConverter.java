/*
 
 
 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.AppUser;
import qmsjee.services.entityServices.interfaces.IUserService;

/**
 *
 * @author Tomek
 */
@FacesConverter("UserConverter")
public class UserConverter implements Converter {

    @Inject
    IUserService userService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value!=null && !value.isEmpty()) {
            return userService.findByID(Long.parseLong(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((AppUser) value).getId().toString();
    }
}
