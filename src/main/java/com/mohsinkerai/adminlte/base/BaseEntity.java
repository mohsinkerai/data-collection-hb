package com.mohsinkerai.adminlte.base;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@Data
@MappedSuperclass
@TypeDefs({
  @TypeDef(name = "json", typeClass = JsonStringType.class),
  @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public abstract class BaseEntity {

  @Id
  @GeneratedValue
  protected Long id;

  @CreatedBy
  protected String createdBy;

  @LastModifiedBy
  protected String updatedBy;

  // https://medium.com/slalom-engineering/optimistically-locking-your-spring-boot-web-services-187662eb8a91
  @Version
  private Long version;
}
