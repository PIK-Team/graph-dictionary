package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.builder.SpringApplicationBuilder;



import java.util.Collections;

@SpringBootApplication
//@EnableNeo4jRepositories
@EnableTransactionManagement
public class SpringApp extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringApp.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringApp.class, args);
	}
}
   
