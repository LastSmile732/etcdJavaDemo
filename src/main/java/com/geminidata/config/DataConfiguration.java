package com.geminidata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.etcd.jetcd.Client;

@Configuration
public class DataConfiguration {

    @Autowired
    private EtcdProperties properties;

    @Bean
    public Client etcdClient() {
        return Client.builder().endpoints(properties.getUris()).build();
    }
}
