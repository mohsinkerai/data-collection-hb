package com.mohsinkerai.adminlte.config.security.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfiguration {

  @Bean
  @Profile("!vaccine-data-upload")
  public AuditorAware<String> auditorProvider() {
    return new MyAuditorAware();
  }

  @Bean("auditorProvider")
  @Profile("vaccine-data-upload")
  public AuditorAware<String> scriptAuditorProvider() {
    return new ScriptAuditorAware();
  }
}
