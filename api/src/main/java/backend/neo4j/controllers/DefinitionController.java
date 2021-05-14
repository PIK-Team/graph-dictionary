package backend.neo4j.controllers;

import backend.neo4j.entities.Definition;
import backend.neo4j.entities.Word;
import backend.neo4j.repositories.DefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/definitions")
public class DefinitionController {

    @Autowired
    DefinitionRepository definitionRepository;

    public DefinitionController(DefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
    }

    @GetMapping
    public Collection<Definition> getAll() {
        return definitionRepository.findAll();
    }

    @PostMapping
    public Definition post(@RequestBody Definition definition){
        return definitionRepository.save(definition);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Collection<Definition> test () {
        return definitionRepository.test();
    }

}
