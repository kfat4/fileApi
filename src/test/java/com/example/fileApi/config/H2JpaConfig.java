package com.example.fileApi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.fileApi.repositories")
@PropertySource("file:src/test/resources/application.properties")
@EnableTransactionManagement
public class H2JpaConfig {
}

