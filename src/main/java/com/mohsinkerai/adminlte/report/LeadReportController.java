package com.mohsinkerai.adminlte.report;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import com.mohsinkerai.adminlte.person.Person;
import com.mohsinkerai.adminlte.person.PersonService;
import com.mohsinkerai.adminlte.report.dto.JamatkhanaAndDateDto;
import com.mohsinkerai.adminlte.report.dto.JamatkhanaSummaryDto;
import com.mohsinkerai.adminlte.report.generator.JamatkhanaRegistrationReportGenerator;
import com.mohsinkerai.adminlte.report.generator.PersonCardReportGenerator;
import com.mohsinkerai.adminlte.report.generator.PersonListReportGenerator;
import com.mohsinkerai.adminlte.report.validator.ReportValidator;
import com.mohsinkerai.adminlte.users.MyUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequestMapping(LeadReportController.REPORT_CONTROLLER_NAME)
@Controller
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('LEAD')")
public class LeadReportController {

  public static final String REPORT_CONTROLLER_NAME = "lead/report";

  private final MyUserService userService;
  private final PersonService personService;
  private final JamatkhanaRegistrationReportGenerator jamatkhanaRegistrationReportGenerator;

  @GetMapping("forms/summary")
  public String findFormsFilledByJamatkhana(Model model) {
    JamatkhanaAndDateDto dto = new JamatkhanaAndDateDto(null, LocalDate.now(), LocalDate.now());

    model.addAttribute("jks", Lists.newArrayList());
    model.addAttribute("data", dto);
    model.addAttribute("urlPath", REPORT_CONTROLLER_NAME + "/forms/summary");

    log.info("Dto is {}", dto);
    return "report/" + "by-jamatkhana";
  }

  @GetMapping("forms/summary/download")
  public HttpEntity<byte[]> getFormsFilledByUsername(JamatkhanaAndDateDto jamatkhanaAndDateDto, Model model) throws JRException {
    LocalDate fromDate = jamatkhanaAndDateDto.getFromDate();
    LocalDate toDate = jamatkhanaAndDateDto.getToDate();

    List<JamatkhanaSummaryDto> jamatkhanaSummary = personService
      .findBySummaryPerAssignedJamatkhanaBetween(userService.getCurrentLoggedInUser().getJamatkhanas(), fromDate, toDate);

    ImmutableMap<String, Object> params = ImmutableMap.of(
      "REPORT_NAME", "Entries by JK Summary",
      "FROM_DATE", Date.valueOf(fromDate),
      "TO_DATE", Date.valueOf(toDate));

    // Return Report Here
    byte[] bytes = jamatkhanaRegistrationReportGenerator.generatePDFReport(jamatkhanaSummary, Maps.newHashMap(params));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + "persons-group-by-jamatkhana" + ".pdf");

    return new HttpEntity<byte[]>(bytes, headers);
  }
}
