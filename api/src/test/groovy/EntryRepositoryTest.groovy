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

import java.util.stream.Collectors

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
        entryRepository.defineChildEntry("SecondWord", "SecondDefinition", "Dictionary", "Word");
        then:
        entryRepository.findByWord("Word", "Dictionary").getSubentries().size()==1;
    }

    def "Find an entry by word and dictionary"()
    {
        when:
        String dictionary = "Dictionary";
        String definition = "Definition";
        String word = "Word";

        Dictionary dict = new Dictionary();
        dict.setDictionaryName(dictionary);


        dictionaryRepository.save(dict);
        entryRepository.defineRootEntry(word, definition, dictionary);

        then:
        entryRepository.findByWord(word, dictionary).getWord().getWord()==word;
    }

    def "Check if no definition"()
    {
        when:
        String dictionary = "Dictionary";
        String definition = "Definition";
        String word = "Word";

        Dictionary dict = new Dictionary();
        dict.setDictionaryName(dictionary);

        dictionaryRepository.save(dict);

        entryRepository.defineRootEntry(word, definition, dictionary);

        then:

        entryRepository.findByWordNoRelationships(dictionary, word).getDefinitions().isEmpty();
    }

    def "create two entries different word same definition"()
    {
        when:
        String dictionary = "Dictionary";
        String definition = "Definition";
        String word1 = "FirstWord";
        String word2 = "SecondWord"

        Dictionary dict = new Dictionary();
        dict.setDictionaryName(dictionary);

        dictionaryRepository.save(dict);

        entryRepository.defineRootEntry(word1, definition, dictionary);
        entryRepository.defineRootEntry(word2, definition, dictionary);

        then:
        entryRepository.findAll().size()==2;
    }

}
