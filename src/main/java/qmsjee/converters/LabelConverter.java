/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import qmsjee.entities.entity.Label;
import qmsjee.services.entityServices.interfaces.ILabelService;

/**
 *
 * @author Tomek
 */
@FacesConverter("LabelConverter")
public class LabelConverter implements Converter {

    @Inject
    ILabelService labelService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        } else {
            return (Label) labelService.findByID(Long.parseLong(value));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Label) value).getId().toString();
    }
}
