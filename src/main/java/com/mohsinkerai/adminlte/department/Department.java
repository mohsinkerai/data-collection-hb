package com.mohsinkerai.adminlte.department;

import com.mohsinkerai.adminlte.base.BaseEntity;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Department extends BaseEntity {

  private String name;
}
