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
import qmsjee.entities.entity.Customer;
import qmsjee.services.entityServices.interfaces.ICustomerService;
import qmsjee.view.controlers.common.CommonMBean;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class CustomerMBean extends CommonMBean implements Serializable {

    private static final long serialVersionUID = 3748710520182013024L;
    @Inject
    ICustomerService customerService;
    private Customer customer;
    private Customer selectedCustomer;
    private List<Customer> customerList;

    @PostConstruct
    public void init() {
        this.customer = new Customer();
    }

    public void addCustomer() {
        customerService.save(this.getCustomer());
        super.setDBMessage(Customer.class.getSimpleName(), "isadded");
        init();
    }

    public void updateCustomer() {
        customerService.update(this.getCustomer());
        super.setDBMessage(Customer.class.getSimpleName(), "isadded");
        init();
    }

    public void delCustomer() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        if (!customerService.delete(selectedCustomer.getId())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, selectedCustomer.getName() + " " + bundle.getString("cantDelete"), ""));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, selectedCustomer.getName() + " " + bundle.getString("isDeleted"), ""));
        }
        this.selectedCustomer = new Customer();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        return customerService.findAll();
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
