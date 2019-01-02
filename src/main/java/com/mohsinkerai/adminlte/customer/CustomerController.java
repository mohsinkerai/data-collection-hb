package com.mohsinkerai.adminlte.customer;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.BaseController;
import com.mohsinkerai.adminlte.base.BaseService;
import com.mohsinkerai.adminlte.department.Department;
import com.mohsinkerai.adminlte.department.DepartmentService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CustomerController.URL_PATH)
public class CustomerController extends BaseController<Customer> {

  public static final String URL_PATH = "customers";

  private final DepartmentService departmentService;

  protected CustomerController(
    BaseService<Customer> baseService,
    DepartmentService departmentService) {
    super(baseService);
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
