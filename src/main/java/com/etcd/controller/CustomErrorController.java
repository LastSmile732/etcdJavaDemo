package com.etcd.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.spring.web.json.Json;

public class CustomErrorController {
  
  private static final String PATH = "/error";
  
  @ApiIgnore
  @RequestMapping(value = PATH)
  Json error(HttpServletRequest request, HttpServletResponse response) {
      Json json = new Json(response.toString());
      return json;
  }
  
  public String getErrorPath() {
    return PATH;
  }
}
