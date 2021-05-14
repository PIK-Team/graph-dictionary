package backend.neo4j.repositories;

import backend.neo4j.entities.Dictionary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "dictionaries", path="dictionaries")
public interface DictionaryRepository extends Repository<Dictionary, Long>
{
    @Query("MATCH (dict: Dictionary) RETURN dict")
    Collection<Dictionary> getDictionaryListNoEntries();

    Dictionary save(Dictionary dict);

    Collection<Dictionary> findAll();
}
