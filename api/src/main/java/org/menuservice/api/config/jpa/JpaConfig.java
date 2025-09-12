package org.menuservice.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "org.menuservice.db")
@EnableJpaRepositories(basePackages = "org.menuservice.db")
public class JpaConfig {
}
