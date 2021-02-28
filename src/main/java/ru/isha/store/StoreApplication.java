package ru.isha.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.isha.store.config.MultiHttpSecurityConfig;
import ru.isha.store.config.WebConfig;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({WebConfig.class, MultiHttpSecurityConfig.class})
@ComponentScan(basePackages = {
		"ru.isha.store.controllers",
		"ru.isha.store.rest",
		"ru.isha.store.services",
		"ru.isha.store.listener",

})
@PropertySource("classpath:/store.properties")
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication springApplication =
				new SpringApplication(StoreApplication.class);
		springApplication.run(args);
	}


}
