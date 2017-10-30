package ru.coursemodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("ru.coursemodel.repository")
@EntityScan("ru.coursemodel.model")
@SpringBootApplication
public class CoursemodelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursemodelApplication.class, args);
	}
}
