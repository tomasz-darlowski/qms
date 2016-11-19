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
import qmsjee.entities.entity.Item;
import qmsjee.services.entityServices.interfaces.IItemService;

/**
 *
 * @author Tomek
 */
@FacesConverter("ItemConverter")
public class ItemConverter implements Converter {

    @Inject
    IItemService itemService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            return (Item) itemService.findByreferenceNumber(value);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Item) value).getReferenceNumber();
    }
}
