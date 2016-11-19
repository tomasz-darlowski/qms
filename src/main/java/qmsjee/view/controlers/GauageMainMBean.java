/*
 
 
 */
package qmsjee.view.controlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import qmsjee.entities.entity.Gauge;
import qmsjee.enums.Navigation;
import qmsjee.services.entityServices.interfaces.IGaugeService;
import qmsjee.view.model.GauageDataModel;

/**
 *
 * @author darlotom
 */
@Named
@ViewScoped
public class GauageMainMBean implements Serializable {

    @Inject
    private IGaugeService gauageService;
    private List<Gauge> gauageList;
    private List<Gauge> gauageTempList;
    private GauageDataModel gauageDataModel;
    private Gauge selectedGauage;
    private String selectedGauageReference;
    private String selectedComponentReference;
    //    SEARCH FIELDS
    private String refNumber;
    private Date dateBefore;
    private Date dateAfter;
    private String activeStatus;
    private String validStatus;

    @PostConstruct
    public void init() {
        resetDefaultSearchVales();
    }

    public void search() {
        if (gauageList != null) {
            gauageList.clear();
        }
        gauageList = gauageService.findBySearchCriteria(refNumber);
        gauageTempList = new ArrayList<>();
        for (Gauge gauage : gauageList) {
            if (gauage.getEvent() != null && (dateAfter != null || dateBefore != null)) {
                if (dateAfter != null && gauage.getEvent().getDateFrom().before(dateAfter) && dateBefore == null) {
                    gauageTempList.add(gauage);
                    continue;
                } else if (dateBefore != null && gauage.getEvent().getDateFrom().after(dateBefore) && dateAfter == null) {
                    gauageTempList.add(gauage);
                    continue;
                } else if (gauage.getEvent().getDateFrom().before(dateAfter) && gauage.getEvent().getDateFrom().after(dateBefore)) {
                    gauageTempList.add(gauage);
                    continue;
                }
            }
            if (activeStatus.equals("active")) {
                if (!gauage.isActive()) {
                    gauageTempList.add(gauage);
                    continue;
                }
            }
            if (activeStatus.equals("inactive")) {
                if (gauage.isActive()) {
                    gauageTempList.add(gauage);
                }
            }
            if (validStatus.equals("valid")) {
                if (gauage.getValidationDate() == null) {
                    gauageTempList.add(gauage);
                }
            }
            if (validStatus.equals("novalid")) {
                if (gauage.getValidationDate() != null) {
                    gauageTempList.add(gauage);
                }
            }
        }
        gauageList.removeAll(gauageTempList);
        gauageDataModel = new GauageDataModel(gauageList);
    }

    public void resetDefaultSearchVales() {
        refNumber = "";
        refNumber = "";
        activeStatus = "active";
        validStatus = "both";
        dateAfter = null;
        dateBefore = null;
    }

    public String create() {
        return Navigation.CREATE.toString();
    }

    public String edit() {
        return Navigation.EDIT.toString();
    }

    public String view() {
        return Navigation.VIEW.toString();
    }

    public List<Gauge> getGauageList() {
        return gauageList;
    }

    public void setGauageList(List<Gauge> gauageList) {
        this.gauageList = gauageList;
    }

    public String getSelectedGauageReference() {
        return selectedGauageReference;
    }

    public void setSelectedGauageReference(String selectedGauageReference) {
        this.selectedGauageReference = selectedGauageReference;
    }

    public String getSelectedComponentReference() {
        return selectedComponentReference;
    }

    public void setSelectedComponentReference(String selectedComponentReference) {
        this.selectedComponentReference = selectedComponentReference;
    }

    public IGaugeService getGauageService() {
        return gauageService;
    }

    public void setGauageService(IGaugeService gauageService) {
        this.gauageService = gauageService;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public GauageDataModel getGauageDataModel() {
        return gauageDataModel;
    }

    public void setGauageDataModel(GauageDataModel gauageDataModel) {
        this.gauageDataModel = gauageDataModel;
    }

    public Gauge getSelectedGauage() {
        return selectedGauage;
    }

    public void setSelectedGauage(Gauge selectedGauage) {
        this.selectedGauageReference = selectedGauage.getReferenceNumber();
        this.selectedGauage = selectedGauage;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }

}
