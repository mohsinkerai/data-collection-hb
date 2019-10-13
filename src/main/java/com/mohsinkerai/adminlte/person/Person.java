package com.mohsinkerai.adminlte.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.mohsinkerai.adminlte.base.BaseEntity;
import com.mohsinkerai.adminlte.config.ProjectConstant;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
public class Person extends BaseEntity {

  private String name;
  private String cnic;
  private String gender;
  private Date dateOfBirth;
  private String residentialAddress;
  private String contactNumber;
  private int houseHoldMembersCount;

  // Do you have any of the following disabilities?
  @ElementCollection
  @CollectionTable(name="disability", joinColumns=@JoinColumn(name="person_id"))
  @Column(name = "disability")
  private List<String> disability = Lists.newArrayList();

  @Max(1)
  @Min(0)
  // In the last 12 months have you visited doctor / hospital for medical checkup?
  private int visitedDoctorForCheckup;

  @Max(1)
  @Min(0)
  // Do you smoke?
  private int smoke;

  @Max(1)
  @Min(0)
  // Insurance Coverage
  private int insuranceCoverage;

  @Max(1)
  @Min(0)
  // Self Covered Insurance
  private int selfInsuranceCoverage;

  @Max(1)
  @Min(0)
  // Employer Covered Insurance
  private int employerInsuranceCoverage;

  @Size(min = 0, max = 15)
  private String otherMedicalFacilityAccessed;

  @ManyToOne
  @JoinColumn(name = "jamatkhana_id")
  private Jamatkhana jamatkhana;

  @DateTimeFormat(pattern = ProjectConstant.DATE_HTML_FORMAT)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ProjectConstant.DATE_FORMAT)
  private LocalDate createdDate;
  private String uuid;
}
