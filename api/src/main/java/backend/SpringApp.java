package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;



import java.util.Collections;

@SpringBootApplication
//@EnableNeo4jRepositories
@EnableTransactionManagement
public class SpringApp extends SpringBootServletInitializer
{
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

<<<<<<< HEAD
    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
=======
    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(SpringApp.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "9090"));
        app.run(args)
        
    }
    
     @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringApp.class);
    }
>>>>>>> ca041653522552395d188092ff0a0203cab31cc5
}
   
