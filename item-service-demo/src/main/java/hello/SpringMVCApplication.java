package hello;

import hello.itemservicedemo.web.validation.ItemValidator;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringMVCApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringMVCApplication.class, args);
	}

//
}
