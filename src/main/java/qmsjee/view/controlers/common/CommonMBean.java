/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package qmsjee.view.controlers.common;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author darlotom
 */
public class CommonMBean {
    
    public void setContextMessage(FacesMessage.Severity severity, String msg, boolean storeMsgInFlash){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        context.addMessage(null, new FacesMessage(severity, msg, ""));
    }
    
    public void setDBMessage(String className, String bundleMsg){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, className+" "+bundle.getString(bundleMsg), ""));
        context.getExternalContext().getFlash().setKeepMessages(true);
    }
    
}
