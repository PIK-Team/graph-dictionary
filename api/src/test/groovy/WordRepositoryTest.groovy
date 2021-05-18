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
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.Neo4jContainer
import spock.lang.Specification
import org.neo4j.harness.ServerControls
import org.neo4j.harness.TestServerBuilders

@Transactional
@SpringBootTest(classes=SpringApp.class)
class WordRepositoryTest extends Specification {

    @Autowired private WordRepository wordRepository

    def setup()
    {
        wordRepository.deleteAll()
    }

    def "check if able to add a word to the database and then retrieve it"() {

        when:
            Word word = new Word()
            word.setWord("word")
            wordRepository.save(word)

        then:
            wordRepository.findAll().contains(word)
    }

    def "add a word five times, see if no copies are present"() {

        when:
            Word word = new Word()
            word.setWord("Tests-Five")
            for(int i = 0; i < 5; ++i) wordRepository.save(word)

        then:
            wordRepository.findByWord(word.getWord()).findAll(w -> w.equals(word)).size()==1
    }

    def "add different Word objects with the same ID, see if only one is returned"(){

        when:
            Word firstWord = new Word()
            Word secondWord = new Word()
            firstWord.setWord("Tests-same-Word")
            secondWord.setWord("Tests-same-Word")
            wordRepository.save(firstWord)
            wordRepository.save(secondWord)
        then:
            wordRepository.findByWord(firstWord.getWord()).size() == 1
    }

    def "delete existing word"(){

        when:
            Word word = new Word()
            word.setWord("Test")
            wordRepository.save(word)
            wordRepository.delete(word)

        then:
            wordRepository.findAll().isEmpty()
    }
}