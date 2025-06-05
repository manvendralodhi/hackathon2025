package com.codeCrew.api.service.impl;

import com.codeCrew.api.entity.Customer;
import com.codeCrew.api.entity.CustomerFullServe;
import com.codeCrew.api.repository.CustomerFsRepository;
import com.codeCrew.api.repository.CustomerRepository;
import com.codeCrew.api.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerFsRepository repositoryFs;

    public CustomerServiceImpl(CustomerRepository repository, CustomerFsRepository repositoryFs) {
        this.repository = repository;
        this.repositoryFs = repositoryFs;
    }

    @Transactional
    public List<Customer> getUsers() {
        return repository.findAll();
    }

    @Transactional
    public Customer addUser(Customer user) {
        try {
            if(user.getCustId() == null) {
                user.setCustId(generateCustid() + "");
            }
            return repository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private long generateCustid() {
        return  (long) (Math.random() * 9000000000L) + 1000000000L;
    }

    @Override
    @Transactional
    public List<Customer> addUsers(List<Customer> users) {
        return repository.saveAll(users);
    }

    @Override
    public Optional<Customer> patchUsers(String custId, Customer customer) {

        return repository.findByCustId(custId).map(cust -> {
            BeanUtils.copyProperties(customer, cust);
            return repository.save(cust);
        });
    }

    @Override
    public Optional<Customer> getUser(String custId) {
        return repository.findByCustId(custId);
    }

    @Override
    public Optional<CustomerFullServe> getFsUser(String custId) {
        return repositoryFs.findByCustId(custId);
    }

    @Override
    public List<CustomerFullServe> getFsUsers() {
        return repositoryFs.findAll();
    }

    @Override
    public String getToken() {
        return "test";
    }
}
