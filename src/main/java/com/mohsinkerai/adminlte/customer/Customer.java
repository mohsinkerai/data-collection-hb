package com.mohsinkerai.adminlte.customer;

import com.mohsinkerai.adminlte.base.BaseEntity;
import com.mohsinkerai.adminlte.department.Department;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Customer extends BaseEntity {

  @Column(nullable = false, length = 40)
  private String firstname;

  @Column(nullable = false, length = 40)
  private String lastname;

  private String email;

  @Column(name = "added_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
  private Timestamp addedDate;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;
}
