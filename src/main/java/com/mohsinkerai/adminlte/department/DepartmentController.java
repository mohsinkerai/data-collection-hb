package com.mohsinkerai.adminlte.department;

import com.google.common.collect.Maps;
import com.mohsinkerai.adminlte.base.BaseController;
import com.mohsinkerai.adminlte.base.BaseService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(DepartmentController.URL_PATH)
public class DepartmentController extends BaseController<Department> {

  public static final String SINGLE_NAME = "department";
  public static final String URL_PATH = "departments";

  protected DepartmentController(
    BaseService<Department> baseService) {
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
  protected String attributeName() {
    return SINGLE_NAME;
  }

  @Override
  protected Department getEmptyObject() {
    return new Department();
  }

  @Override
  protected Map<String, Object> getAttributes() {
    return Maps.newHashMap();
  }
}
