package com.mohsinkerai.adminlte.service;

import com.mohsinkerai.adminlte.model.Customer;
import com.mohsinkerai.adminlte.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer, Long> {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  protected JpaRepository<Customer, Long> getRepository() {
    return customerRepository;
  }
}