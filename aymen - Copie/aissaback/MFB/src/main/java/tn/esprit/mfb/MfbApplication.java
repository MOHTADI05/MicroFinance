package tn.esprit.mfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
public class MfbApplication {

	public static void main(String[] args) {

		SpringApplication.run(MfbApplication.class, args);
	}

}
