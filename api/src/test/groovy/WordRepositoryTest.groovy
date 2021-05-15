import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Neo4jContainer
import spock.lang.Specification
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;

@DataNeo4jTest
class WordRepositoryTest extends Specification {

    private static Neo4j embeddedDatabaseServer;

    def setupSpec()
    {
        neo4jContainer = new Neo4jContainer<>().withAdminPassword("romek");
        neo4jContainer.start();
    }

    def cleanupSpec()
    {
        neo4jContainer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    def "adding word to the database"(@Autowired Neo4jClient client) {

        given:
        Optional<Long> result = client.query("MATCH (n) RETURN COUNT(n)")
                .fetchAs(Long.class)
                .one();
        expect:
            result.get() == 0L;
    }
}