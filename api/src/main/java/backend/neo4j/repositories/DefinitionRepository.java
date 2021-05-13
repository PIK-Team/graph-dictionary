package backend.neo4j.repositories;


import backend.neo4j.entities.Definition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "definitions", path="definitions")
public interface DefinitionRepository extends Neo4jRepository<Definition, Long>
{
}
