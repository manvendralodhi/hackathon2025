package com.codeCrew.api.controller;

import com.codeCrew.api.entity.Customer;
import com.codeCrew.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bapp")
//@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/getToken")
    public String getToken() {
        return service.getToken();
    }

    @GetMapping("/{custId}")
    public Customer getUsers(@PathVariable String custId) {
        return service.getUser(custId).orElse(null);
    }

    @GetMapping("/findAll")
    public List<Customer> getUsers() {
        return service.getUsers();
    }

    @PostMapping
    public Customer addUser(@RequestBody Customer user) {
        return service.addUser(user);
    }

    @PostMapping("/addBulk")
    public List<Customer> addUsers(@RequestBody List<Customer> users) {
        return service.addUsers(users);
    }

    @PatchMapping("/{custId}")
    public Customer updateUsers(@PathVariable String custId, @RequestBody Customer customer) {
        return service.patchUsers(custId, customer).orElse(null);
    }
}
