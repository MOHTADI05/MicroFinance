package tn.fintech.fintech.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("tn.fintech.fintech.domain")
@EnableJpaRepositories("tn.fintech.fintech.repos")
@EnableTransactionManagement
public class DomainConfig {
}
