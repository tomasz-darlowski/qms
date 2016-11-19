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
import qmsjee.entities.entity.Issue;
import qmsjee.services.entityServices.interfaces.IIssueService;

/**
 *
 * @author Tomek
 */
@FacesConverter("issueConverter")
public class IssueConverter implements Converter {

    @Inject
    IIssueService issueService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            return (Issue) issueService.findByID(Long.valueOf(value));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        return ((Issue) value).getId().toString();
    }

}
