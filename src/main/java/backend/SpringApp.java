package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringApp {

    public static void main(String[] args) {
        System.out.println("DUPA");
        SpringApplication.run(SpringApp.class, args);
        System.out.println("DUPA");
    }

    @GetMapping("/")
    public String sayHello() {
        return "Hello world! Spring boot";
    }
}
