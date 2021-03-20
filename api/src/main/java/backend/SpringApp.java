package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableNeo4jRepositories
public class SpringApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringApp.class, args);
    }

//    @GetMapping("/hello")
//    public String sayHello() {
//
//        return "Hello world! Spring boot";
//    }


}
