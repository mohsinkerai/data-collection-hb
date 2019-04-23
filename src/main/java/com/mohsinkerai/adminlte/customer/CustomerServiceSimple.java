package com.mohsinkerai.adminlte.customer;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceSimple extends SimpleBaseService<Customer> {

  protected CustomerServiceSimple(
    SimpleBaseRepository<Customer> simpleBaseRepository) {
    super(simpleBaseRepository);
  }
}
