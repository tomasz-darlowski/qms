/*
 
 
 */
package qmsjee.services.entityServices.impl;

import java.io.Serializable;
import javax.inject.Named;
import qmsjee.entities.entity.Customer;
import qmsjee.services.commons.dao.GenericService;
import qmsjee.services.entityServices.interfaces.ICustomerService;

/**
 *
 * @author darlotom
 */
@Named
public class CustomerService extends GenericService<Customer, Long> implements ICustomerService, Serializable {


    public CustomerService() {
        super(Customer.class);
    }
}
