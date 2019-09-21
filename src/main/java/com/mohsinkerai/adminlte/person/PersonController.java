package com.mohsinkerai.adminlte.person;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import com.mohsinkerai.adminlte.jamatkhana.JamatkhanaService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PersonController.URL_PATH)
public class PersonController extends SimpleBaseController<Person> {

  public static final String URL_PATH = "person";

  private final JamatkhanaService jamatkhanaService;

  protected PersonController(
    SimpleBaseService<Person> simpleBaseService,
    JamatkhanaService jamatkhanaService) {
    super(simpleBaseService);
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
  protected Person getEmptyObject() {
    return new Person();
  }

  @Override
  protected Map<String, Object> getAttributes() {
    List<Jamatkhana> jamatkhanas = jamatkhanaService.findAll();
    return ImmutableMap.of("jamatkhanas", jamatkhanas);
  }
}
