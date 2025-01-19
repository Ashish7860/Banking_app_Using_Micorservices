package com.nagarro.configuration_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigurationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationManagementApplication.class, args);
	}

}
