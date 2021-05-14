package backend.neo4j.repositories;

import backend.neo4j.entities.Word;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "words", path = "words")
public interface WordRepository extends Repository<Word, Long>
{

    Collection<Word> findAll();

    Word save(Word word);
}
