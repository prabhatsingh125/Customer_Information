package com.prabs1.prabz.controller;

import com.prabs1.prabz.exception.CustomerNotFoundException;
import com.prabs1.prabz.model.Customer;
import com.prabs1.prabz.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAll()
    {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getById(@PathVariable Long id) throws CustomerNotFoundException
    {
        return customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException(id));


    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer)
    {
        return customerRepository.save(customer);
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws CustomerNotFoundException
    {
        Customer customer1=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(id));

        customer1.setName(customer.getName());
        customer1.setMobileNumber(customer.getMobileNumber());
        customer1.setEmailId(customer.getEmailId());
        customer1.setAddress(customer.getAddress());

        Customer updatedCustomer=customerRepository.save(customer1);

        return updatedCustomer;

    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException
    {
        Customer customer=customerRepository.findById(id)
                .orElseThrow(()->new CustomerNotFoundException(id));

        customerRepository.delete(customer);

        return ResponseEntity.ok().build();
    }



}
