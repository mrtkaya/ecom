package com.definex.ecom.Controller;

import com.definex.ecom.Entity.CustomerRequest;
import com.definex.ecom.Services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping(value = { "/customer/createCustomer" }, produces = "application/json", method = RequestMethod.POST)
    public void createCustomer(@RequestBody CustomerRequest customer) {
        logger.info("Controller:  createCustomer");
        customerService.createCustomer(customer);
    }

    @RequestMapping(value = { "/customer/updateCustomer" }, produces = "application/json", method = RequestMethod.POST)
    public void deleteCustomer(@RequestBody CustomerRequest customer) {
        logger.info("Controller:  deleteCustomer");
        customerService.updateCustomer(customer);
    }
}
