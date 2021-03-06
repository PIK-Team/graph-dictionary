package backend.neo4j.repositories;

import backend.neo4j.entities.Dictionary;
import backend.neo4j.entities.Entry;
import backend.neo4j.entities.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "entries", path = "entries")
public interface EntryRepository extends Repository<Entry, Long>
{
    @Query( "MATCH (dict: Dictionary {dictionaryName: $dictionary} )\n" +
            "MATCH (dict) -[:INCLUDES]-> (parent: Entry)" +
            "MATCH(parent) -[:DEFINES]-> (Word{word: $parentWord})\n" +
            "MERGE(word:Word{word: $inputWord})\n" +
            "MERGE( (entry:Entry) -[:DEFINES]-> (word) )\n" +
            "CREATE(definition: Definition{definition: $inputDefinition})\n" +
            "CREATE((entry) -[:MEANS]-> (definition) )\n" +
            "MERGE( (parent) -[:CATEGORIZES]-> (entry) )\n" +
            "MERGE( (dict) -[:INCLUDES]-> (entry) )"
    )
    void defineChildEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition,
                          @Param ("dictionary") String dictionary, @Param("parentWord") String parentWord);


    @Query("" +
            "MATCH (dict: Dictionary {dictionaryName: $dictionary} )" +
            "MERGE(word:Word{word: $inputWord})\n" +
            "CREATE(definition: Definition{definition: $inputDefinition})\n" +
            "CREATE((entry:Entry) -[:MEANS]-> (definition) ) " +
            "MERGE( (entry) -[:DEFINES]-> (word) )"+
            "MERGE( (dict) -[:INCLUDES]-> (entry) )"
    )
    void defineRootEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition,
                         @Param ("dictionary") String dictionary);


    @Query("MATCH (dict: Dictionary {dictionaryName: $dictionary} ) -[:INCLUDES]-> (e: Entry)\n" +
            "MATCH(e) -[:DEFINES]-> (Word{word: $inputWord})\n" +
            "CREATE(definition: Definition{definition: $inputDefinition})\n" +
            "MERGE((e) -[:MEANS]-> (definition) )" +
            "MATCH(e) -[rel_def:MEANS]-> (alldefs: Definition)" +
            "RETURN e, collect(rel_def), collect(alldefs)"
    )
    List<Entry> addDefinition(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition,
                          @Param ("dictionary") String dictionary);


    // Te zapytania nie pobieraja zwiazkow glebszych niz 1
    // na razie nie potrzebne
    @Query(" MATCH(Dictionary{dictionaryName: $dictionaryName}) -[:INCLUDES]-> (input: Entry)\n" +
            "MATCH (input) -[:DEFINES]-> (Word{word:$word})" +
            "MATCH (parent: Entry) - [:CATEGORIZES] -> (input)\n" +
            "MATCH (parent) -[:CATEGORIZES] -> (related: Entry)\n" +
            "WHERE NOT related = input\n" +
            "OPTIONAL MATCH (related) - [relationship] -> (target)" +
            "OPTIONAL MATCH (target) - [subrelationship] -> (subtarget)\n" +
            "WHERE NOT target = input\n" +
            "RETURN  related, collect(relationship), collect(target), collect(subrelationship)")
    Collection<Entry> getRelated(@Param("word") String word, @Param("dictionaryName") String dictionaryName);


    @Query("MATCH (e: Entry)" +
            "OPTIONAL MATCH (e)-[relationship]->(child)" +
            "RETURN e, collect(relationship), collect(child)")
    Collection<Entry> myFindAll();

    Collection<Entry> findAll();


    @Query(" MATCH(Dictionary{dictionaryName: $dictionaryName}) -[:INCLUDES]-> (e: Entry)\n" +
            "MATCH (e)-[:DEFINES]->(w: Word{word : $word})\n" +
            "MATCH (e)-[rel]->(target)\n" +
            "RETURN e, collect(rel), collect(target)")
    Entry findByWord(@Param("word") String word, @Param("dictionaryName") String dictionaryName);




    @Query("MATCH(Dictionary{dictionaryName: $dictName}) -[:INCLUDES]-> (e: Entry)" +
            "MATCH (e)-[:DEFINES]->(w: Word{word : $entryName})\n" +
            "RETURN e")
    Entry findByWordNoRelationships(@Param("dictName") String dictName, @Param("entryName") String entryName);


    // nie dziala :)
    @Query("MATCH(Dictionary{dictionaryName: $dictName}) -[:INCLUDES]-> (e: Entry)\n" +
            "MATCH(e) -[:DEFINES]-> (word: Word{word: $entryName})\n" +
            "MATCH(e) -[:MEANS] -> (def: Definition)\n" +
            "OPTIONAL MATCH(e) -[:CATEGORIZES]-> (child: Entry)\n" +
            "OPTIONAL MATCH(child) -[child_word_rel:DEFINES]-> (child_word)\n" +
            "OPTIONAL MATCH(parent: Entry) -[parent_rel:CATEGORIZES *1..]-> (e)\n" +
            "MATCH(parent) -[parent_word_rel:DEFINES]-> (parent_word)\n" +
            "RETURN  collect(parent), collect(parent_rel), collect(e), collect(parent_word_rel), collect(parent_word)")
        //"RETURN e, word, collect(def), collect(child), collect(child_word_rel), collect(child_word),collect(parent), collect(parent_word_rel), collect(parent_word)")
    //e, word, collect(def), collect(child), collect(child_word_rel), collect(child_word),
    Collection<Entry> entryOverview(@Param("dictName") String dictName, @Param("entryName") String entryName);


    @Query("     MATCH(Dictionary{dictionaryName: $dictName}) -[:INCLUDES]-> (e: Entry)\n" +
            "    MATCH(e) -[:DEFINES]-> (word: Word{word: $entryName})\n" +
            "    MATCH(parent: Entry) -[:CATEGORIZES]-> (e)\n" +
            "    MATCH(parent) -[word_rel:DEFINES]-> (parent_word)\n" +
            "    RETURN parent, collect(word_rel), collect(parent_word)")
    public Entry getParentWordOnly(@Param("dictName") String dictName, @Param("entryName") String entryName);


    @Query("    MATCH(Dictionary{dictionaryName: $dictName}) -[:INCLUDES]-> (e: Entry)\n" +
            "    MATCH(e) -[:DEFINES]-> (word: Word{word: $entryName})\n" +
            "    MATCH(e) -[:CATEGORIZES]-> (child: Entry)\n" +
            "    MATCH(child) -[def:DEFINES]-> (child_word)\n" +
            "    RETURN child, collect(def), collect(child_word)")
    public List<Entry> getChildrenWordsOnly(@Param("dictName") String dictName, @Param("entryName") String entryName);


    @Query("    MATCH(Dictionary{dictionaryName: $dictName}) -[:INCLUDES]-> (e: Entry)\n" +
        "       MATCH (e)- [rel :DEFINES]->(w: Word)\n" +
        "       WHERE w.word ENDS WITH $affix OR w.word STARTS WITH $affix" +
        "       RETURN w.word")
    public List<String> getHintsByAffix(@Param("dictName") String dictName, @Param("affix") String affix);

    void deleteAll();

    void deleteEntryById(Long id);
}
