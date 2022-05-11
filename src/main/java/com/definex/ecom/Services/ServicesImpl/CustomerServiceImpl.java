package com.definex.ecom.Services.ServicesImpl;

import com.definex.ecom.Entity.CustomerRequest;
import com.definex.ecom.Repository.CustomerRequestRepository;
import com.definex.ecom.Services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRequestRepository customerRequestRepository;

    @Override
    public void createCustomer(CustomerRequest customer) {
        customerRequestRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerRequest customer) {
        customerRequestRepository.save(customer);
    }

}
