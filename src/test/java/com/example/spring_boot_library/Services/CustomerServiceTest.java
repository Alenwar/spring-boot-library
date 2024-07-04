package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.CustomerDataIsNullException;
import com.example.spring_boot_library.ExceptionConfig.CustomerNotFoundException;
import com.example.spring_boot_library.Model.Customer;
import com.example.spring_boot_library.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCustomerWithNullData() {
        Customer customer = new Customer();
        customer.setId("1");
        CustomerDataIsNullException exception = assertThrows(
                CustomerDataIsNullException.class, () -> customerService.save(customer));
        assertEquals("Customer name:null, email:null at least one field is null.", exception.getMessage());
    }

    @Test
    void saveCustomerWithValidData() {
        Customer customer = new Customer();
        customer.setName("Test Customer 1");
        customer.setEmail("testemail@example.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.save(customer);

        assertEquals(customer, savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void findNonExistentCustomerById() {
        String customerId = "nonExistentCustomerId";
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class, () -> customerService.findById(customerId).
                orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found.")));
        assertEquals("Customer with ID " + customerId + " not found.", exception.getMessage());
    }

}
