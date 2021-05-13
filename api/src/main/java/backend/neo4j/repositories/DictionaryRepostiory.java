package backend.neo4j.repositories;

import backend.neo4j.entities.Dictionary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "dictionaries", path="dictionaries")
public interface DictionaryRepostiory extends Neo4jRepository<Dictionary, Long>
{
}
