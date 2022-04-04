package toy.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.boot.starter.autoconfigure.SpringfoxConfigurationProperties;
import toy.lms.config.SwaggerConfig;

@SpringBootApplication
@EnableScheduling
@Import(SwaggerConfig.class)
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }

}
