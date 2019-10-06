package com.mohsinkerai.adminlte.person;

import com.mohsinkerai.adminlte.base.SimpleBaseService;
import com.mohsinkerai.adminlte.users.MyUser;
import com.mohsinkerai.adminlte.users.MyUserService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class PersonService extends SimpleBaseService<Person> {

  private final PersonRepository personRepository;
  private final MyUserService myUserService;

  protected PersonService(
    PersonRepository personRepository, MyUserService myUserService) {
    super(personRepository);
    this.personRepository = personRepository;
    this.myUserService = myUserService;
  }

  public List<Person> findByCreatedByAndCreatedOn(String username, LocalDate fromDate,
    LocalDate toDate) {
    return personRepository.findByCreatedByAndCreatedDateBetween(username, fromDate, toDate);
  }

  @Override
  public Person save(Person person) {
    if (person.getId() == null || person.getCreatedDate() == null) {
      person.setCreatedDate(LocalDate.now());
    } else if (!isPersonEditAllowed(person.getId())) {
      throw new RuntimeException("Person Editing Not Allowed");
    }
    return super.save(person);
  }

  public boolean isPersonEditAllowed(Long personId) {
    Person person = findOne(personId)
      .orElseThrow(() -> new RuntimeException(String.format("Id %d Doesn't Exist", personId)));
    MyUser currentLoggedInUser = myUserService.getCurrentLoggedInUser();
    boolean admin = isAdmin(currentLoggedInUser);
    return admin || ChronoUnit.DAYS.between(person.getCreatedDate(), LocalDate.now()) < 2;
  }

  private boolean isAdmin(MyUser currentUser) {
    return currentUser.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .map(String::toLowerCase)
      .filter(s -> s.equals("ADMIN"))
      .findAny()
      .isPresent();
  }
}
