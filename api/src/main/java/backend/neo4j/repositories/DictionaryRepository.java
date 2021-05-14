package backend.neo4j.repositories;

import backend.neo4j.entities.Dictionary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

//@RepositoryRestResource(collectionResourceRel = "dictionaries", path="dictionaries")
public interface DictionaryRepository extends Neo4jRepository<Dictionary, Long>
{
    @Query("MATCH (dict: Dictionary) RETURN dict")
    Collection<Dictionary> getDictionaryListNoEntries();

}
