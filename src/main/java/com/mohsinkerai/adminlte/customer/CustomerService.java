package com.mohsinkerai.adminlte.customer;

import com.mohsinkerai.adminlte.base.BaseRepository;
import com.mohsinkerai.adminlte.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<Customer> {

  protected CustomerService(
    BaseRepository<Customer> baseRepository) {
    super(baseRepository);
  }
}
