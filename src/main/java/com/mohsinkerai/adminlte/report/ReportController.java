package com.mohsinkerai.adminlte.report;

import static com.mohsinkerai.adminlte.report.ReportController.REPORT_CONTROLLER_NAME;

import com.mohsinkerai.adminlte.person.Person;
import com.mohsinkerai.adminlte.person.PersonService;
import com.mohsinkerai.adminlte.report.dto.UsernameAndDateDto;
import com.mohsinkerai.adminlte.users.MyUser;
import com.mohsinkerai.adminlte.users.MyUserService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(REPORT_CONTROLLER_NAME)
@Controller
@AllArgsConstructor
@Slf4j
public class ReportController {

  public static final String REPORT_CONTROLLER_NAME = "report";

  private final MyUserService userService;
  private final PersonService personService;

  @GetMapping("forms/by-username")
  public String findFormsFilledByUsername(Model model) {
    UsernameAndDateDto dto = new UsernameAndDateDto(null, LocalDate.now());

    List<String> userNames = userService.findAll().stream().map(MyUser::getUsername)
      .collect(Collectors.toList());

    model.addAttribute("users", userNames);
    model.addAttribute("data", dto);
    model.addAttribute("urlPath", REPORT_CONTROLLER_NAME + "/forms/by-username");

    log.info("Dto is {}", dto);
    return REPORT_CONTROLLER_NAME + "/by-username";
  }

  @GetMapping("forms/by-username/download")
  public String getFormsFilledByUsername(UsernameAndDateDto usernameAndDateDto, Model model) {
    LocalDate localDate = usernameAndDateDto.getDate();
    String username = usernameAndDateDto.getUsername();

    List<Person> persons = personService
      .findByCreatedByAndCreatedOn(username, localDate);
    log.info("Got DTO {}, persons {}", usernameAndDateDto, persons);
    // Return Report Here
    return REPORT_CONTROLLER_NAME + "/by-username";
  }
}
