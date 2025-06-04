package com.codeCrew.api.service.impl;

import com.codeCrew.api.entity.Customer;
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

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Customer> getUsers() {
        return repository.findAll();
    }

    @Transactional
    public Customer addUser(Customer user) {
        return repository.save(user);
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
}
