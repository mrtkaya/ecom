package com.definex.ecom.Services;

import com.definex.ecom.Entity.CustomerRequest;

public interface CustomerService {
    void createCustomer(CustomerRequest customer);
    void updateCustomer(CustomerRequest customer);

}
