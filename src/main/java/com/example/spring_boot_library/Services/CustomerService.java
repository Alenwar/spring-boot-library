package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.CustomerDataIsNullException;
import com.example.spring_boot_library.ExceptionConfig.CustomerNotFoundException;
import com.example.spring_boot_library.Model.Customer;
import com.example.spring_boot_library.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(String id) {
        if (customerRepository.findById(id).isPresent()) {
            return customerRepository.findById(id);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
    }

    public Customer save(Customer customer) {
        if  (customer.getName() == null || customer.getEmail() == null){
            throw new CustomerDataIsNullException("Customer name:" + customer.getName() + ", email:" + customer.getEmail()
                    + " at least one field is null.");
        } else{
            return customerRepository.save(customer);
        }
    }

    public void deleteById(String id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
    }
}