package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



import java.util.Collections;

@SpringBootApplication
@EnableNeo4jRepositories
public class SpringApp extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringApp.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "9090"));
        app.run(args);
    }
}

