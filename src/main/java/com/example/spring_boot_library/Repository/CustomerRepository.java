package com.example.spring_boot_library.Repository;

import com.example.spring_boot_library.Model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}