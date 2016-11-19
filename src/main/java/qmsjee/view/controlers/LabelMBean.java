/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.Label;
import qmsjee.services.entityServices.interfaces.ILabelService;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class LabelMBean implements Serializable {

    @Inject
    ILabelService labelService;
    private String strLabel;

    @PostConstruct
    public void init() {
        strLabel = "";
    }

    public void addLabeltoDB() {
        List<Label> allLabels = labelService.findAll();
        boolean flag = false;
        if (null != strLabel) {
            for (Label object : allLabels) {
                if (object.getLabel().equals(strLabel)) {
                    flag = true;
                }
            }
            if (!flag) {
                labelService.save(new Label(strLabel));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Label already exist", ""));
            }
        }
        strLabel = "";
    }

    public String getStrLabel() {
        return strLabel;
    }

    public void setStrLabel(String strLabel) {
        this.strLabel = strLabel;
    }

}
