package com.mohsinkerai.adminlte.config.security;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource datasource;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/bootstrap/**", "/dist/**", "/plugins/**").permitAll()
      .antMatchers("/department*/**").hasAuthority("ADMIN")
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .failureUrl("/login?error")
      .loginPage("/login")
      .defaultSuccessUrl("/")
      .permitAll()
      .and()
      .logout()
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
      .logoutSuccessUrl("/login")
      .permitAll();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //Use Spring Boots User detailsMAnager
    JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();

    //Set our Datasource to use the one defined in application.properties
    userDetailsService.setDataSource(datasource);

    //Create BCryptPassword encoder
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    //add components
    auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    auth.jdbcAuthentication().dataSource(datasource);

    // add new user "user" with password "password" - password will be encrypted
    if (!userDetailsService.userExists("user")) {
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority("USER"));
      User userDetails = new User("user", encoder.encode("user"), authorities);
      userDetailsService.createUser(userDetails);
    }

    if (!userDetailsService.userExists("admin")) {
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority("ADMIN"));
      authorities.add(new SimpleGrantedAuthority("USER"));
      User userDetails = new User("admin", encoder.encode("admin"), authorities);
      userDetailsService.createUser(userDetails);
    }
  }

}
