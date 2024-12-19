package org.example.productmanagementbyspringmvc.service;

import org.example.productmanagementbyspringmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHibernateICustomerService {
    List<Customer> findAll();

    void save(Customer customer);

    Customer findById(int id);

    void remove(int id);
}
