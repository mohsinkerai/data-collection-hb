package com.mohsinkerai.adminlte.users;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(MyUserController.URL_PATH)
public class MyUserController extends SimpleBaseController<MyUser> {

  public static final String URL_PATH = "users";

  protected MyUserController(
    SimpleBaseService<MyUser> baseService) {
    super(baseService);
  }

  @Override
  public String edit(Long id, Model model) {
    throw new RuntimeException("Updates are not allowed");
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
  protected MyUser getEmptyObject() {
    return new MyUser();
  }

  @Override
  protected Map<String, Object> getAttributes() {
    return ImmutableMap.of();
  }
}
