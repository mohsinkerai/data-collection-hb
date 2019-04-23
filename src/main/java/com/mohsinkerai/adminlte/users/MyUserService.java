package com.mohsinkerai.adminlte.users;

import com.mohsinkerai.adminlte.base.SimpleBaseService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserService extends SimpleBaseService<MyUser> implements UserDetailsService,
  UserDetailsManager {

  private final MyUserRepository myUserRepository;
  private final PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;

  public MyUserService(
    MyUserRepository myUserRepository,
    PasswordEncoder passwordEncoder) {
    super(myUserRepository);
    this.myUserRepository = myUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return myUserRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Unable to find username " + username));
  }

  public Optional<MyUser> findByUsername(String username) {
    return myUserRepository.findByUsername(username);
  }

  @Override
  public MyUser save(MyUser myUser) {
    myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
    return super.save(myUser);
  }

  @Override
  public void createUser(UserDetails user) {
    save(user);
  }

  @Override
  public void updateUser(UserDetails user) {
    save(user);
  }

  @Override
  public void deleteUser(String username) {
    findByUsername(username).ifPresent(user -> {
      user.setEnabled(false);
      super.save(user);
    });
  }

  @Override
  public void changePassword(String oldPassword, String newPassword) {
    Authentication currentUser = SecurityContextHolder.getContext()
      .getAuthentication();

    if (currentUser == null) {
      // This would indicate bad coding somewhere
      throw new AccessDeniedException(
        "Can't change password as no Authentication object found in context "
          + "for current user.");
    }

    String username = currentUser.getName();

    // If an authentication manager has been set, re-authenticate the user with the
    // supplied password.
    if (authenticationManager != null) {
      log.debug("Reauthenticating user '" + username
        + "' for password change request.");

      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        username, oldPassword));
    }
    else {
      log.debug("No authentication manager set. Password won't be re-checked.");
    }

    log.debug("Changing password for user '" + username + "'");

    MyUser myUser = findByUsername(username).get();

    myUser.setPassword(newPassword);

    SecurityContextHolder.getContext().setAuthentication(
      createNewAuthentication(currentUser, myUser));
  }

  @Override
  public boolean userExists(String username) {
    return findByUsername(username).isPresent();
  }

  private void save(UserDetails user) {
    if (user instanceof MyUser) {
      save((MyUser) user);
      return;
    }

    Optional<MyUser> optionalMyUser = findByUsername(user.getUsername());
    MyUser myUser = optionalMyUser.orElse(new MyUser());
    myUser.apply(user);
    save(myUser);
  }

  protected Authentication createNewAuthentication(Authentication currentAuth, MyUser myUser) {
    UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
      myUser.getUsername(), null, myUser.getAuthorities());
    newAuthentication.setDetails(currentAuth.getDetails());

    return newAuthentication;
  }

  public void setAuthenticationManager(
    AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
}
