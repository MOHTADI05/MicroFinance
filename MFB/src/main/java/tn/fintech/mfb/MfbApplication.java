package tn.fintech.mfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan("tn.fintech.mfb.entity")
public class MfbApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MfbApplication.class, args);
    }

}
