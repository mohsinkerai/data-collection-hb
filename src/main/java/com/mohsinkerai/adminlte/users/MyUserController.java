package com.mohsinkerai.adminlte.users;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import com.mohsinkerai.adminlte.jamatkhana.JamatkhanaService;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
  private final JamatkhanaService jamatkhanaService;

  protected MyUserController(
    MyUserService myUserService,
    JamatkhanaService jamatkhanaService) {
    super(myUserService);
    this.myUserService = myUserService;
    this.jamatkhanaService = jamatkhanaService;
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
    List<Jamatkhana> jamatkhanas = jamatkhanaService.findAll();
    return ImmutableMap.of("jks", jamatkhanas);
  }

  @GetMapping("hello")
  public void hello() {
    log.info("Hello World");
    log.info("Role {}", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
  }
}
