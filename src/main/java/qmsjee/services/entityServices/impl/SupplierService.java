/*
 
 
 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import javax.inject.Named;
import qmsjee.entities.entity.Supplier;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.ISupplierService;

/**
 *
 * @author Tomek
 */
@Named
public class SupplierService extends GenericService<Supplier, Long> implements ISupplierService, Serializable {

    private static final long serialVersionUID = 7894108465192235879L;

    public SupplierService() {
        super(Supplier.class);
    }
}
