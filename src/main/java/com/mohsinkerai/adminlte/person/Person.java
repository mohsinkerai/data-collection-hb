package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.BaseEntity;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Person extends BaseEntity {

  private String name;
  private String cnic;
  private String gender;
  private Date dateOfBirth;
  private String residentialAddress;
  private String contactNumber;

  // In the last 12 months have you visited doctor / hospital for medical checkup?, If yes, Type of
  //facility accessed? (Multiple Selection)
//  private List<String> visitedDoctorRecently;

  // Have you been diagnosed or prescribed medicine for any disease? (Multiple Selection)
//  private List<String> diagnosedDiseaseOrMedicine;

  // Do you have access to health insurance? If yes, From where: (Multiple Selection)
//  private List<String> currentHealthInsurance;

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
}
