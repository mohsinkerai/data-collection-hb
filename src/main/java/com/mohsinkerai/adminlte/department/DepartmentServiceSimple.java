package com.mohsinkerai.adminlte.department;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceSimple extends SimpleBaseService<Department> {

  protected DepartmentServiceSimple(
    SimpleBaseRepository<Department> simpleBaseRepository) {
    super(simpleBaseRepository);
  }
}
