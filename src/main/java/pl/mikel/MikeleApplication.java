package pl.mikel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import java.util.Collections;


@SpringBootApplication
@EnableAsync
public class MikeleApplication  {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MikeleApplication.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.port","8808"));
        springApplication.run(args);
    }

}
