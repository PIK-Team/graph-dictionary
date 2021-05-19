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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/words")
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
    public void post(@RequestBody Word word){
        wordRepository.save(word);
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        wordRepository.deleteAll();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteByWord(@RequestBody Word word) {
        wordRepository.delete(word);
    }

}
