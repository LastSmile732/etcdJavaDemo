package com.etcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.etcd"})
public class EtcdMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtcdMasterApplication.class, args);
	}

}
