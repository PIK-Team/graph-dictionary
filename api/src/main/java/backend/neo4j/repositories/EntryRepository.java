package backend.neo4j.repositories;

import backend.neo4j.entities.Entry;
import backend.neo4j.entities.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "entries", path = "entries")
public interface EntryRepository extends Repository<Entry, Long>
{
    @Query("" +
            "MATCH(word:Word{word: $inputWord})\n" +
            "MATCH(definition: Definition{definition: $inputDefinition})\n" +
            "MATCH(parent: Entry) WHERE id(parent) = $parent_id "+
            "MATCH (dict: Dictionary {dictionaryName: $dictionary} )" +
            "CREATE((entry:Entry) -[:MEANS]-> (definition) ) " +
            "MERGE( (entry) -[:DEFINES]-> (word) )" +
            "MERGE( (parent) -[:CATEGORIZES]-> (entry) )" +
            "MERGE( (dict) -[:INCLUDES]-> (entry) )"
    )
    void defineChildEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition,
                          @Param ("dictionary") String dictionary, @Param("parent_id") Long parent_id);

    @Query("" +
            "MATCH(word:Word{word: $inputWord})\n" +
            "MATCH(definition: Definition{definition: $inputDefinition})\n" +
            "MATCH (dict: Dictionary {dictionaryName: $dictionary} )" +
            "CREATE((entry:Entry) -[:MEANS]-> (definition) ) " +
            "MERGE( (entry) -[:DEFINES]-> (word) )"+
            "MERGE( (dict) -[:INCLUDES]-> (entry) )"
    )
    void defineRootEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition,
                         @Param ("dictionary") String dictionary);


    // Te zapytania nie pobieraja zwiazkow glebszych niz 1
    @Query("MATCH (input: Entry) WHERE id(input) = $input_id\n" +
            "MATCH (parent: Entry) - [:CATEGORIZES] -> (input)\n" +
            "MATCH (parent) -[:CATEGORIZES] -> (related: Entry)\n" +
            "WHERE NOT related = input\n" +
            "OPTIONAL MATCH (related) - [relationship] -> (target)" +
            "OPTIONAL MATCH (target) - [subrelationship] -> (subtarget)\n" +
            "WHERE NOT target = input\n" +
            "RETURN  related, collect(relationship), collect(target), collect(subrelationship)")
    Collection<Entry> getRelated(@Param("input_id") Long input_id);

    @Query("MATCH (e: Entry)" +
            "OPTIONAL MATCH (e)-[relationship]->(child)" +
            "RETURN e, collect(relationship), collect(child)")
    Collection<Entry> myFindAll();

    Collection<Entry> findAll();

    @Query("MATCH (e: Entry)-[:DEFINES]->(w: Word{word : $word})\n" +
            "MATCH (e)-[rel]->(target)\n" +
            "RETURN e, collect(rel), collect(target)")
    Collection<Entry> findByWord(@Param("word") String word);
}
