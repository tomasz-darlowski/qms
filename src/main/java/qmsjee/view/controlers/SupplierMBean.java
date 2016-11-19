/*
 
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.Supplier;
import qmsjee.services.entityServices.interfaces.ISupplierService;
import qmsjee.view.controlers.common.CommonMBean;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class SupplierMBean extends CommonMBean implements Serializable {

    private static final long serialVersionUID = -4438923489745060775L;
    @Inject
    ISupplierService supplierService;
    private List<Supplier> allSupliers;
    private Supplier supplier;
    private Supplier selectedSupplier;

    @PostConstruct
    public void init() {
        supplier = new Supplier();
    }

    public void addSupplier() {
        supplierService.save(this.getSupplier());
        super.setDBMessage(Supplier.class.getSimpleName(), "isadded");
        init();
    }

    public void updateSupplier() {
        supplierService.update(this.getSupplier());
        super.setDBMessage(Supplier.class.getSimpleName(), "isadded");
        init();
    }

    public void delSupplier() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        if (!supplierService.delete(selectedSupplier.getId())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, selectedSupplier.getName() + " " + bundle.getString("cantDelete"), ""));
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, selectedSupplier.getName() + " " + bundle.getString("isDeleted"), ""));
        }
        this.selectedSupplier = new Supplier();
    }

    public List<Supplier> getAllSupliers() {
        return supplierService.findAll();
    }

    public void setAllSupliers(List<Supplier> allSupliers) {
        this.allSupliers = allSupliers;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSelectedSupplier() {
        return selectedSupplier;
    }

    public void setSelectedSupplier(Supplier selectedSupplier) {
        this.selectedSupplier = selectedSupplier;
    }

}
