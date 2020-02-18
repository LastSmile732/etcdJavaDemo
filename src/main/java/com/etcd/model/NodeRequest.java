package com.etcd.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NodeRequest {

  private String hostname;
  
  private String platform;
  
  private String ip;
  
  private String environmentID;
  
  public NodeRequest(@JsonProperty("hostname")String hostname, 
      @JsonProperty("platform")String platform, 
      @JsonProperty("ip")String ip, 
      @JsonProperty("environmentID")String environmentID) {
    this.hostname = hostname;
    this.platform = platform;
    this.ip = ip;
    this.environmentID = environmentID;
    
  }
  
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getEnvironmentID() {
    return environmentID;
  }

  public void setEnvironmentID(String environmentID) {
    this.environmentID = environmentID;
  }
}
