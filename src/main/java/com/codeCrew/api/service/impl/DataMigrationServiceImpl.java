package com.codeCrew.api.service.impl;

import com.codeCrew.api.entity.Customer;
import com.codeCrew.api.entity.CustomerFullServe;
import com.codeCrew.api.repository.CustomerFsRepository;
import com.codeCrew.api.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataMigrationServiceImpl {

    private final CustomerRepository repository;
    private final CustomerFsRepository repositoryFs;

    public DataMigrationServiceImpl(CustomerRepository repository, CustomerFsRepository repositoryFs) {
        this.repository = repository;
        this.repositoryFs = repositoryFs;
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void migrateData(){

        try{
            List<Customer> regCustomers = repository.findAll();
            List<CustomerFullServe> fsCustomers = regCustomers.stream().map(this::mapToFs).collect(Collectors.toList());

//            repositoryFs.saveAll(fsCustomers);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private CustomerFullServe mapToFs(Customer customer) {
        CustomerFullServe customerFs = new CustomerFullServe();
        BeanUtils.copyProperties(customer, customerFs);
        customerFs.setId(null);
        saveOrUpdate(customerFs);
        return customerFs;
    }

    @Transactional
    public CustomerFullServe saveOrUpdate(CustomerFullServe customerFs) {
        Optional<CustomerFullServe> existingCustomer = repositoryFs.findByCustId(customerFs.getCustId());
        try {
            if (existingCustomer.isPresent()) {
                CustomerFullServe updatedCustomer = existingCustomer.get();
                updatedCustomer.setPreferredDeviceFlag(customerFs.isPreferredDeviceFlag()); // Update fields
                return repositoryFs.save(updatedCustomer);
            } else {
                return repositoryFs.save(customerFs); // Insert new record
            }
        } catch (Exception e) {
            System.out.println(e);
        }
       return null;
    }

}
