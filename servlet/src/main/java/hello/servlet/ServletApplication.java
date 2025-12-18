package hello.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //서블릿 자동 등록
@SpringBootApplication
@Slf4j
public class ServletApplication {

	public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
	}

}
