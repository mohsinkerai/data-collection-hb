package com.mohsinkerai.adminlte.users;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import com.mohsinkerai.adminlte.users.authority.MyAuthority;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(MyUserController.URL_PATH)
public class MyUserController extends SimpleBaseController<MyUser> {

  public static final String URL_PATH = "users";
  private final MyUserService myUserService;

  protected MyUserController(
    MyUserService myUserService) {
    super(myUserService);
    this.myUserService = myUserService;
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
