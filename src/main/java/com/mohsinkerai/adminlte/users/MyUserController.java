package com.mohsinkerai.adminlte.users;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
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

  @GetMapping("hello")
  public void hello() {
    log.info("Hello World");
    log.info("Role {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
  }
}
