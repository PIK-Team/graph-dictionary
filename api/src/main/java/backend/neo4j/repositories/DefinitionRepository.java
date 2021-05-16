package backend.neo4j.repositories;


import backend.neo4j.entities.Definition;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "definitions", path="definitions")
public interface DefinitionRepository extends Repository<Definition, Long>
{
    @Query("MATCH (d:Definition) RETURN d LIMIT 2")
    Collection<Definition> test();

    Collection<Definition> findAll();
    void save(Definition definition);
    void deleteAll();
    void deleteDefinitionById(Long id);


}
