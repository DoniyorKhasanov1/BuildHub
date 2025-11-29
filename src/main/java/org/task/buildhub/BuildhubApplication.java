package org.task.buildhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.task.buildhub.repository")
@SpringBootApplication
@Slf4j
public class BuildhubApplication {
	public static void main(String[] args) {
		SpringApplication.run(BuildhubApplication.class, args);
        log.info("APPLICATION STARTED ==== {}", BuildhubApplication.class.getName());
	}

}
