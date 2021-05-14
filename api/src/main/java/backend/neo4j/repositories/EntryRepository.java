package backend.neo4j.repositories;

import backend.neo4j.entities.Entry;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

//@RepositoryRestResource(collectionResourceRel = "entries", path = "entries")
public interface EntryRepository extends Neo4jRepository<Entry, Long>
{
    @Query("" +
            "MATCH(word:Word{word: $inputWord})\n" +
            "MATCH(definition: Definition{definition: $inputDefinition})\n" +
            "MATCH(parent: Entry) WHERE id(parent) = $parent_id "+
            "CREATE((entry:Entry) -[:MEANS]-> (definition) ) " +
            "MERGE( (entry) -[:DEFINES]-> (word) )" +
            "MERGE( (parent) -[:CATEGORIZES]-> (entry) )")
    void defineChildEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition, @Param("parent_id") Long parent_id);

    @Query("" +
            "MATCH(word:Word{word: $inputWord})\n" +
            "MATCH(definition: Definition{definition: $inputDefinition})\n" +
            "CREATE((entry:Entry) -[:MEANS]-> (definition) ) " +
            "MERGE( (entry) -[:DEFINES]-> (word) )")
    void defineRootEntry(@Param("inputWord") String inputWord, @Param("inputDefinition") String inputDefinition);


    // Te zapytania nie pobieraja zwiazkow glebszych niz 1
    @Query("MATCH (input: Entry) WHERE id(input) = $input_id\n" +
            "MATCH (parent: Entry) - [:CATEGORIZES] -> (input)\n" +
            "MATCH (parent) -[:CATEGORIZES] -> (related: Entry)\n" +
            "OPTIONAL MATCH (related) - [relationship] -> (target)" +
            "WHERE NOT related = input\n" +
            "RETURN  related, collect(relationship), collect(target)")
    Collection<Entry> getRelated(@Param("input_id") Long input_id);

    @Query("MATCH (e: Entry)" +
            "OPTIONAL MATCH (e)-[relationship]->(child)" +
            "RETURN e, collect(relationship), collect(child)")
    Collection<Entry> myFindAll();
}
