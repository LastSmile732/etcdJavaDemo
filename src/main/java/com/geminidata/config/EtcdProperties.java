package com.geminidata.config;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("etcd")
public class EtcdProperties {

    private List<URI> uris = new ArrayList<>();

    public List<URI> getUris() {
      return uris;
    }

    public void setUris(List<URI> uris) {
      this.uris = uris;
    }
}
