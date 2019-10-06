package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends SimpleBaseRepository<Person> {

  List<Person> findByCreatedByAndCreatedDateBetween(String createdBy, LocalDate fromCreatedDate, LocalDate toCreatedDate);
}
