package ru.isha.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/store.properties")
public class StoreApplication {

	public static void main(String[] args) {

		SpringApplication springApplication =
				new SpringApplication(StoreApplication.class);
		springApplication.run(args);
	}
}
