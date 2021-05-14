package backend.neo4j.controllers;


import backend.neo4j.entities.Definition;
import backend.neo4j.entities.Word;
import backend.neo4j.repositories.DefinitionRepository;
import backend.neo4j.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/words2")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping
    public Collection<Word> getAll() {
        return wordRepository.findAll();
    }

    @PostMapping
    public Word post(@RequestBody Word word){
        return wordRepository.save(word);
    }




}
