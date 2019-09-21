package com.mohsinkerai.adminlte.jamatkhana;

import com.mohsinkerai.adminlte.base.BaseEntity;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Jamatkhana extends BaseEntity {

  private String name;
}
