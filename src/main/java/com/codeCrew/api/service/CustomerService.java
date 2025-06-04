package com.codeCrew.api.service;

import com.codeCrew.api.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getUsers();

    Customer addUser(Customer user);

    List<Customer> addUsers(List<Customer> users);

    Optional<Customer> patchUsers(String id, Customer customer);

    Optional<Customer> getUser(String id);
}
