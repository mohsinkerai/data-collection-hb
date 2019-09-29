package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.SimpleBaseRepository;
import com.mohsinkerai.adminlte.base.SimpleBaseService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends SimpleBaseService<Person> {

  private final PersonRepository personRepository;

  protected PersonService(
    PersonRepository personRepository) {
    super(personRepository);
    this.personRepository = personRepository;
  }

  public List<Person> findByCreatedByAndCreatedOn(String username, LocalDate createdOn) {
    return personRepository.findByCreatedByAndCreatedDate(username, createdOn);
  }

  @Override
  public Person save(Person person) {
    if(person.getId() == null || person.getCreatedDate() == null) {
      person.setCreatedDate(LocalDate.now());
    }
    return super.save(person);
  }
}
