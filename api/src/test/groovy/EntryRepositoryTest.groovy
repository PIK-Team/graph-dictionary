import backend.SpringApp
import backend.neo4j.entities.Dictionary
import backend.neo4j.entities.Entry
import backend.neo4j.repositories.DefinitionRepository
import backend.neo4j.repositories.DictionaryRepository
import backend.neo4j.repositories.EntryRepository
import backend.neo4j.repositories.WordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest(classes=SpringApp.class)
class EntryRepositoryTest extends Specification {

    @Autowired private EntryRepository entryRepository;
    @Autowired private WordRepository  wordRepository;
    @Autowired private DefinitionRepository definitionRepository;
    @Autowired private DictionaryRepository dictionaryRepository;

    def setup() {
        entryRepository.deleteAll();
        wordRepository.deleteAll();
        definitionRepository.deleteAll();
        dictionaryRepository.deleteAll();
    }

    def "Create a root entry, see if correctly created"(){
        when:
        Dictionary dict = new Dictionary();
        dict.setDictionaryName("Dictionary");
        dictionaryRepository.save(dict);
        entryRepository.defineRootEntry("Word", "Definition", "Dictionary");
        then:
        entryRepository.findAll().size() == 1 && wordRepository.findAll().size() == 1
            && definitionRepository.findAll().size() == 1 && dictionaryRepository.findAll().size() == 1;
    }

    def "Create a root entry, then create it's child, see if child has been added to parent"() {
        when:
        Dictionary dict = new Dictionary();
        dict.setDictionaryName("Dictionary");
        dictionaryRepository.save(dict);
        entryRepository.defineRootEntry("Word", "Definition", "Dictionary");
        Long id = entryRepository.findByWord("Word")[0].getId();
        entryRepository.defineChildEntry("SecondWord", "SecondDefinition", "Dictionary", id);
        then:
        entryRepository.findByWord("Word")[0].getSubentries().size()==1;
    }
}
