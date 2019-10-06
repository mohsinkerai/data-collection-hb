package com.mohsinkerai.adminlte.report;

import static com.mohsinkerai.adminlte.report.ReportController.REPORT_CONTROLLER_NAME;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mohsinkerai.adminlte.person.Person;
import com.mohsinkerai.adminlte.person.PersonService;
import com.mohsinkerai.adminlte.report.dto.UsernameAndDateDto;
import com.mohsinkerai.adminlte.report.generator.PersonListReportGenerator;
import com.mohsinkerai.adminlte.users.MyUser;
import com.mohsinkerai.adminlte.users.MyUserService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(REPORT_CONTROLLER_NAME)
@Controller
@AllArgsConstructor
@Slf4j
public class ReportController {

  public static final String REPORT_CONTROLLER_NAME = "report";

  private final MyUserService userService;
  private final PersonService personService;
  private final PersonListReportGenerator personListReportGenerator;

  @GetMapping("forms/by-username")
  public String findFormsFilledByUsername(Model model) {
    UsernameAndDateDto dto = new UsernameAndDateDto(null, LocalDate.now(), LocalDate.now());

    List<String> userNames = userService.findAll().stream().map(MyUser::getUsername)
      .collect(Collectors.toList());

    model.addAttribute("users", userNames);
    model.addAttribute("data", dto);
    model.addAttribute("urlPath", REPORT_CONTROLLER_NAME + "/forms/by-username");

    log.info("Dto is {}", dto);
    return REPORT_CONTROLLER_NAME + "/by-username";
  }

  @GetMapping("forms/by-username/download")
  public HttpEntity<byte[]> getFormsFilledByUsername(UsernameAndDateDto usernameAndDateDto, Model model)
    throws JRException {
    LocalDate fromDate = usernameAndDateDto.getFromDate();
    LocalDate toDate = usernameAndDateDto.getToDate();
    String username = usernameAndDateDto.getUsername();

    List<Person> persons = personService
      .findByCreatedByAndCreatedOn(username, fromDate, toDate);
    log.info("Got DTO {}, persons {}", usernameAndDateDto, persons);

    ImmutableMap<String, Object> params = ImmutableMap.of(
      "REPORT_NAME", "Entries by " + username,
      "FROM_DATE", Date.valueOf(fromDate),
      "TO_DATE", Date.valueOf(toDate));

    // Return Report Here
    byte[] bytes = personListReportGenerator.generatePDFReport(persons, Maps.newHashMap(params));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + "persons-by-username-on-time" + ".pdf");

    return new HttpEntity<byte[]>(bytes, headers);
  }
}
