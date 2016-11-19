/*
 
 
 */
package qmsjee.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import qmsjee.services.entityServices.impl.CustomerService;
import qmsjee.services.entityServices.interfaces.ICustomerService;

@WebListener
public class DataPopulateServlet implements ServletContextListener {

    
    ICustomerService customerService = new CustomerService();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (int i = 0; i < 10; i++) {
//            customerService.save(new Customer("Customer "+i, "contactPerson"+i, "address"+i, "email"+i+"@email.com", i+"123456789"));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}