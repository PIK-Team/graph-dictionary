package backend.neo4j.repositories;

import backend.neo4j.entities.Entry;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "entries", path = "entries")
public interface EntryRepostiory extends Neo4jRepository<Entry, Long>
{
}
