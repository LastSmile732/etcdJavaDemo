package com.geminidata.data.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class Node implements Serializable{
  
  private UUID id;
  
  private String hostname;
  
  private String platform;
  
  private String ip;
  
  private String environmentID;
  
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
