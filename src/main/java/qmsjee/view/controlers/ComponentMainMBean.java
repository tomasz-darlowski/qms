/*
 
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.Component;
import qmsjee.entities.entity.Customer;
import qmsjee.entities.entity.Supplier;
import qmsjee.enums.Navigation;
import qmsjee.services.entityServices.interfaces.IComponentService;
import qmsjee.view.model.ComponentDataModel;

/**
 *
 * @author Tomek
 */
@Named
@ViewScoped
public class ComponentMainMBean implements Serializable {

    private static final long serialVersionUID = 104717284831762293L;
    @Inject
    private IComponentService componentService;
    private List<Component> allComponentList;
    private ComponentDataModel componentModel;
    private Component selectedComponent;
    private String selectedComponentReference;
//    SEARCH FIELDS
    private String name;
    private String refNumber;
    private Supplier supplier;
    private Customer customer;
    private String partType;
    private String activeStatus;

    @PostConstruct
    public void init() {
        resetDefaultSearchVales();
    }

    public String create() {
        return Navigation.CREATE.toString();
    }

    public void search() {
        if (allComponentList != null) {
            allComponentList.clear();
        }
        allComponentList = componentService.findBySearchCriteria(name, refNumber);
        List<Component> compToRemove = new ArrayList<>();
        for (Component component : allComponentList) {
            if (customer != null) {
                if (!component.getCustomer().equals(customer)) {
                    compToRemove.add(component);
                    continue;
                }
            }
            if (supplier != null) {
                if (!component.getSupplier().equals(supplier)) {
                    compToRemove.add(component);
                    continue;
                }

            }
            if (partType.equals("final")) {
                if (!component.isFinalProduct()) {
                    compToRemove.add(component);
                    continue;
                }
            }
            if (partType.equals("sub")) {
                if (component.isFinalProduct()) {
                    compToRemove.add(component);
                    continue;
                }
            }
            if (activeStatus.equals("active")) {
                if (!component.isActive()) {
                    compToRemove.add(component);
                    continue;
                }
            }
            if (activeStatus.equals("inactive")) {
                if (component.isActive()) {
                    compToRemove.add(component);
                    continue;
                }
            }
        }
        allComponentList.removeAll(compToRemove);
        componentModel = new ComponentDataModel(allComponentList);

    }

    public void resetDefaultSearchVales() {
        name = "";
        refNumber = "";
        partType = "both";
        activeStatus = "active";
        supplier = null;
        customer = null;
    }
//<editor-fold defaultstate="collapsed" desc="SETTERS AND GETTERS">

    public List<Component> getAllComponentList() {
        return allComponentList;
    }

    public void setAllComponentList(List<Component> allComponentList) {
        this.allComponentList = allComponentList;
    }

    public String getSelectedComponentReference() {
        return selectedComponentReference;
    }

    public void setSelectedComponentReference(String selectedComponentReference) {
        this.selectedComponentReference = selectedComponentReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPartType() {
        return partType;
    }

    public void setPartType(String partType) {
        this.partType = partType;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Component getSelectedComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(Component selectedComponent) {
        if (selectedComponent != null) {
            this.selectedComponentReference = selectedComponent.getReferenceNumber();
        }
        this.selectedComponent = selectedComponent;
    }

    public ComponentDataModel getComponentModel() {
        return componentModel;
    }

    public void setComponentModel(ComponentDataModel componentModel) {
        this.componentModel = componentModel;
    }

    //</editor-fold>
}
