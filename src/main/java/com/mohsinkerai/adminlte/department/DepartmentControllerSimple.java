package com.mohsinkerai.adminlte.department;

import com.google.common.collect.Maps;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(DepartmentControllerSimple.URL_PATH)
public class DepartmentControllerSimple extends SimpleBaseController<Department> {

  public static final String URL_PATH = "departments";

  protected DepartmentControllerSimple(
    SimpleBaseService<Department> simpleBaseService) {
    super(simpleBaseService);
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
  protected Department getEmptyObject() {
    return new Department();
  }

  @Override
  protected Map<String, Object> getAttributes() {
    return Maps.newHashMap();
  }
}
