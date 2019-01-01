package com.mohsinkerai.adminlte.customer;

import com.google.common.collect.Maps;
import com.mohsinkerai.adminlte.base.BaseController;
import com.mohsinkerai.adminlte.base.BaseService;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CustomerController.URL_PATH)
public class CustomerController extends BaseController<Customer> {

  public static final String URL_PATH = "customers";

  protected CustomerController(
    BaseService<Customer> baseService) {
    super(baseService);
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
    return Maps.newHashMap();
  }
}
