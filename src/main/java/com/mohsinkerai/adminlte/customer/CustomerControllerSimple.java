package com.mohsinkerai.adminlte.customer;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import com.mohsinkerai.adminlte.department.Department;
import com.mohsinkerai.adminlte.department.DepartmentServiceSimple;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CustomerControllerSimple.URL_PATH)
public class CustomerControllerSimple extends SimpleBaseController<Customer> {

  public static final String URL_PATH = "customers";

  private final DepartmentServiceSimple departmentService;

  protected CustomerControllerSimple(
    SimpleBaseService<Customer> simpleBaseService,
    DepartmentServiceSimple departmentService) {
    super(simpleBaseService);
    this.departmentService = departmentService;
  }

  @Override
  protected String urlPath() {
    return URL_PATH;
  }

  @Override
  protected String viewPath() {
    return URL_PATH;
  }

  @Override
  protected Customer getEmptyObject() {
    return new Customer();
  }

  @Override
  protected Map<String, Object> getAttributes() {
    List<Department> departments = departmentService.findAll();
    return ImmutableMap.of("departments", departments);
  }
}
