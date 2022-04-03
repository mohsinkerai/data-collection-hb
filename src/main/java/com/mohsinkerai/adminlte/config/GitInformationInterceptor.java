package com.mohsinkerai.adminlte.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Component
@AllArgsConstructor
public class GitInformationInterceptor extends HandlerInterceptorAdapter {

  private final GitProperties gitProperties;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                         ModelAndView modelAndView) throws Exception {
    super.postHandle(request, response, handler, modelAndView);

    if (modelAndView != null) {

      modelAndView.getModel()
        .put("gitCommitId", gitProperties.get("commit.id.describe").replace("dirty", "ip"));
      modelAndView.getModel().put("gitBuildTime",
        Instant.ofEpochMilli(Long.valueOf(gitProperties.get("build.time"))).atZone(ZoneId.of("Asia/Karachi")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
  }

}
