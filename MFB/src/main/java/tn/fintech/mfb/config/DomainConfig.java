package tn.fintech.mfb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("tn.fintech.mfb.domain")
@EnableJpaRepositories("tn.fintech.mfb.repos")
@EnableTransactionManagement
public class DomainConfig {
}
