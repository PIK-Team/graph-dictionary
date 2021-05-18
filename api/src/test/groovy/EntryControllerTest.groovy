import backend.SpringApp
import backend.neo4j.controllers.EntryController
import backend.neo4j.entities.Dictionary
import backend.neo4j.entities.Entry
import backend.neo4j.repositories.DefinitionRepository
import backend.neo4j.repositories.DictionaryRepository
import backend.neo4j.repositories.EntryRepository
import backend.neo4j.repositories.WordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
import static java.util.Map.entry;



@Transactional
@SpringBootTest(classes= SpringApp.class)
class EntryControllerTest extends Specification{

    @Autowired private EntryRepository entryRepository
    @Autowired private WordRepository wordRepository
    @Autowired private DefinitionRepository definitionRepository
    @Autowired private DictionaryRepository dictionaryRepository
    @Autowired private EntryController controller;

    def setup() {
        entryRepository.deleteAll()
        wordRepository.deleteAll()
        definitionRepository.deleteAll()
        dictionaryRepository.deleteAll()
    }

    def "An entry can be saved" (){
        when:
        Dictionary dict = new Dictionary()

        String dictName = "Dictionary";
        String word = "Word";
        dict.setDictionaryName(dictName);
        dictionaryRepository.save(dict)
        Map<String, String> params = Map.ofEntries(
                entry("word", word),
                entry("definition", "def2"),
                entry("dictionary", dictName)
        )
        ResponseEntity responseEntity = controller.define(params);
        then:
        responseEntity.statusCode == HttpStatus.OK &&
                entryRepository.findAll().size() == 1 && wordRepository.findAll().size() == 1
                && definitionRepository.findAll().size() == 1 && dictionaryRepository.findAll().size() == 1;
    }


    def "Dictionary cannot contain multiple entries defining the same word"(){
        when:
        Dictionary dict = new Dictionary()

        String dictName = "Dictionary";
        String word = "Word";
        dict.setDictionaryName(dictName);
        dictionaryRepository.save(dict)
        entryRepository.defineRootEntry(word, "Definition", dictName)
        Map<String, String> params = Map.ofEntries(
                entry("word", word),
                entry("definition", "def2"),
                entry("dictionary", dictName)
        )

        ResponseEntity responseEntity = controller.define(params);

        then:
        responseEntity.statusCode == HttpStatus.CONFLICT &&
            entryRepository.findAll().size() == 1 && wordRepository.findAll().size() == 1
                && definitionRepository.findAll().size() == 1 && dictionaryRepository.findAll().size() == 1;
    }

    def "The same word can be defined in different dictionaries"(){
        when:
        Dictionary dict = new Dictionary()
        Dictionary dict2 = new Dictionary()

        String dictName = "Dictionary";
        String dictName2 = "Dict2";
        dict.setDictionaryName(dictName);
        dict2.setDictionaryName(dictName2)
        dictionaryRepository.save(dict)
        dictionaryRepository.save(dict2)

        String word = "Word";
        entryRepository.defineRootEntry(word, "Definition", dictName)

        Map<String, String> params = Map.ofEntries(
                entry("word", word),
                entry("definition", "def2"),
                entry("dictionary", dictName2)
        )

        ResponseEntity responseEntity = controller.define(params);

        then:
        responseEntity.statusCode == HttpStatus.OK &&
                entryRepository.findAll().size() == 2
    }

}
