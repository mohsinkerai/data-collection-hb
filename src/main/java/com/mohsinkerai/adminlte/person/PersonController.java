package com.mohsinkerai.adminlte.person;

import com.google.common.collect.ImmutableMap;
import com.mohsinkerai.adminlte.base.SimpleBaseController;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import com.mohsinkerai.adminlte.jamatkhana.JamatkhanaService;
import com.mohsinkerai.adminlte.users.MyUserService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(PersonController.URL_PATH)
public class PersonController extends SimpleBaseController<Person> {

  public static final String URL_PATH = "person";

  private final JamatkhanaService jamatkhanaService;
  private final MyUserService myUserService;
  private final PersonService personService;

  protected PersonController(
    PersonService personService,
    JamatkhanaService jamatkhanaService,
    MyUserService myUserService) {
    super(personService);
    this.jamatkhanaService = jamatkhanaService;
    this.myUserService = myUserService;
    this.personService = personService;
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
    return ImmutableMap.of("jks", jamatkhanas);
  }

  @Override
  public String edit(@PathVariable Long id, Model model) {
    boolean canEditPerson = personService.isPersonEditAllowed(id);
    if (!canEditPerson) {
      throw new RuntimeException("Person has Exceeded 2 Days and Cannot be Edited");
    }
    return super.edit(id, model);
  }

  @Override
  public String save(Person person, BindingResult bindingResult, Model model,
    RedirectAttributes ra) {
    super.save(person, bindingResult, model, ra);
    ra.addFlashAttribute("successFlash", String
      .format("Successfully Saved Jamati Memeber with Name <b>%s</b> and id </b>%d</b>", person.getName(),
        person.getId()));
    return "redirect:/" + urlPath() + "/add";
  }
}
