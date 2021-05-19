import backend.SpringApp
import backend.neo4j.entities.Definition
import backend.neo4j.repositories.DefinitionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest(classes=SpringApp.class)
class DefinitionRepositoryTest extends Specification {

    @Autowired
    private DefinitionRepository repository

    def setup(){
        repository.deleteAll()
    }

    def "store an entry inside of repository and be able to retrieve it"(){

        when:
            Definition definition = new Definition()
            definition.setDefinition("Definicja")
            repository.save(definition)
        then:
            repository.findAll().contains(definition)
    }

    def "store an entry inside of a repository, delete it and see if result is an empty collection"(){

        when:
            Definition definition = new Definition()
            definition.setDefinition("Definicja")
            repository.save(definition)
            repository.delete(definition)
        then:
            repository.findAll().isEmpty()
    }

    def "store a few of the same definitions, see if correct count returns"(){

        when:
            Definition definition1 = new Definition()
            definition1.setDefinition("Definicja")
            Definition definition2 = new Definition()
            definition2.setDefinition("Definicja")
            Definition definition3= new Definition()
            definition3.setDefinition("Definicja")
            repository.save(definition1)
            repository.save(definition2)
            repository.save(definition3)
        then:
            repository.findAll().size()==3
    }
}