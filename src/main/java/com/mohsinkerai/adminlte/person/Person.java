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

//   In the last 12 months have you visited doctor / hospital for medical checkup?, If yes, Type of
//  facility accessed? (Multiple Selection)
  @ElementCollection
  @CollectionTable(name="visited_doctor_recently", joinColumns=@JoinColumn(name="person_id"))
  @Column(name = "visited_doctor_recently")
  private List<String> visitedDoctorRecently = Lists.newArrayList();

  // Have you been diagnosed or prescribed medicine for any disease? (Multiple Selection)
  @ElementCollection
  @CollectionTable(name="diagnosed_disease_or_medicine", joinColumns=@JoinColumn(name="person_id"))
  @Column(name = "diagnosed_disease_or_medicine")
  private List<String> diagnosedDiseaseOrMedicine = Lists.newArrayList();

  // Do you have access to health insurance? If yes, From where: (Multiple Selection)
  @ElementCollection
  @CollectionTable(name="current_health_insurance", joinColumns=@JoinColumn(name="person_id"))
  @Column(name = "current_health_insurance")
  private List<String> currentHealthInsurance = Lists.newArrayList();

  //Do you have under 5 year children in your family? if yes, are there following basic vaccinations
  //complete?
  private int followingVaccinations;

  // How many day in a week do you eat food from outside / resturants?
  private int daysEatingOutsideFood;

  // How many hours in a week do you exercise?
  @Column(name="hours_a_week_you_exercise")
  private int hoursAWeekYouExercise;

  @ManyToOne
  @JoinColumn(name = "jamatkhana_id")
  private Jamatkhana jamatkhana;

  @DateTimeFormat(pattern = ProjectConstant.DATE_HTML_FORMAT)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ProjectConstant.DATE_FORMAT)
  private LocalDate createdDate;

  private String uuid;
}
