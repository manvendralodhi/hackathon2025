package com.codeCrew.api.controller;

import com.codeCrew.api.entity.Customer;
import com.codeCrew.api.entity.CustomerFullServe;
import com.codeCrew.api.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fs")
//@RequiredArgsConstructor
public class FsController {
    private final CustomerService service;

    public FsController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{custId}")
    public CustomerFullServe getUsers(@PathVariable String custId) {
        return service.getFsUser(custId).orElse(null);
    }

    @GetMapping("/findAll")
    public List<CustomerFullServe> getUsers() {
        return service.getFsUsers();
    }


}
