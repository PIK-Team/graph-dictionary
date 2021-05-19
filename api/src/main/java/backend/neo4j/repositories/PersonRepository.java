package backend.neo4j.repositories;


import backend.neo4j.entities.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends Neo4jRepository<Person, Long> {

}