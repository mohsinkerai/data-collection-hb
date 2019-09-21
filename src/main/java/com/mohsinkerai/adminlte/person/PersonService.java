package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends SimpleBaseService<Person> {

  protected PersonService(
    SimpleBaseRepository<Person> simpleBaseRepository) {
    super(simpleBaseRepository);
  }
}
