package backend.neo4j.controllers;

import backend.neo4j.entities.Dictionary;
import backend.neo4j.entities.Word;
import backend.neo4j.repositories.DefinitionRepository;
import backend.neo4j.repositories.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("dictionaries")
public class DictionaryController {
    @Autowired
    DictionaryRepository dictionaryRepository;

    public DictionaryController(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @PostMapping
    public Dictionary post(@RequestBody Dictionary dict){
        return dictionaryRepository.save(dict);
    }

    @GetMapping
    public Collection<Dictionary> getAll() {
        return dictionaryRepository.findAll();
    }

    @GetMapping("/dicts")
    public Collection<Dictionary> getDictionaryListNoEntries(){
        return dictionaryRepository.getDictionaryListNoEntries();
    }
}
