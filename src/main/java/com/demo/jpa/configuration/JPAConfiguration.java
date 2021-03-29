package com.demo.jpa.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.demo.jpa.entity")
@EnableJpaRepositories("com.demo.jpa.*")
@Configuration
public class JPAConfiguration {
}
