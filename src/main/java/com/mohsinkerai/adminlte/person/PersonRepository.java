package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import com.mohsinkerai.adminlte.jamatkhana.Jamatkhana;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface PersonRepository extends SimpleBaseRepository<Person> {

  List<Person> findByCreatedByAndCreatedDateBetween(String createdBy, LocalDate fromCreatedDate, LocalDate toCreatedDate);

  List<Person> findByJamatkhanaIn(Collection<Jamatkhana> jamatkhanas);
}
