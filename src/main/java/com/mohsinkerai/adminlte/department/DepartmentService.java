package com.mohsinkerai.adminlte.department;

import com.mohsinkerai.adminlte.base.BaseRepository;
import com.mohsinkerai.adminlte.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Department> {

  protected DepartmentService(
    BaseRepository<Department> baseRepository) {
    super(baseRepository);
  }
}
