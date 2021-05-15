import backend.SpringApp
import backend.neo4j.entities.Word
import backend.neo4j.repositories.WordRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.neo4j.harness.TestServerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.neo4j.core.Neo4jClient
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Neo4jContainer
import spock.lang.Specification
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;

@SpringBootTest(classes=SpringApp.class)
class WordRepositoryTest extends Specification {

    @Autowired private WordRepository wordRepository;

    def "adding word to the database"() {

        when:
            Word word = new Word();
            word.setWord("Test");
            wordRepository.save(word);

        then:
            wordRepository.findFirstByWord(word.getWord()).getWord().equals(word.getWord());
    }
}