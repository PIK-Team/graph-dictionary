package backend.neo4j.controllers;

import backend.neo4j.entities.Entry;
import backend.neo4j.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("entries")
public class EntryController {

    @Autowired
    EntryRepository entryRepository;

    public EntryController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    /**
     * Called like this:
     * curl -i -X POST -H "Content-Type:application/json" -d '{"word" : "testura", "definition": "very strong testing situation"}' http://localhost:9090/entries2/define
     */
    @RequestMapping(value = "/define", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity define(@RequestBody Map<String, String> params) {

        String word_param = "word";
        String dictionary_param = "dictionary";
        String parent_param = "parent_entry";
        String definition_param = "definition";

        // if word already exists in the specified dictionary return error
        if (entryRepository.findByWordNoRelationships(params.get(dictionary_param),  params.get(word_param)) != null)
            return new ResponseEntity(HttpStatus.CONFLICT);

        if (params.get(parent_param) != null)
            entryRepository.defineChildEntry(params.get(word_param), params.get(definition_param), params.get(dictionary_param) ,params.get(parent_param));
        else
            entryRepository.defineRootEntry(params.get(word_param), params.get(definition_param), params.get(dictionary_param));

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public Collection<Entry> getAll() {
        return entryRepository.findAll();
    }


    @GetMapping("{dictionary}/{word}/related")
    public Collection<Entry> getRelated(@PathVariable String word, @PathVariable String dictionary){
        return entryRepository.getRelated(word, dictionary);
    }

    @GetMapping("/findall")
    public Collection<Entry> myFindAll(){
        return entryRepository.myFindAll();
    }

    @GetMapping("{word}/{dictionary}/findByWord")
    public Entry findByWord(@PathVariable String word, @PathVariable String dictionary){
        return entryRepository.findByWord(word, dictionary);
    }

    @GetMapping("/deleteAll")
    void deleteAll() {
        entryRepository.deleteAll();
    }

    @GetMapping("{dictName}/{entryName}/overview")
    public Entry entryOverview(@PathVariable String dictName, @PathVariable String entryName) {

        Entry currentEntry;
        Entry parent;

        List<Entry> children = entryRepository.getChildrenWordsOnly(dictName, entryName);
        currentEntry = findByWord(entryName, dictName);
        currentEntry.setSubentries(children);

        while ((parent = entryRepository.getParentWordOnly(dictName, currentEntry.getWord().getWord())) != null){
            parent.addEntry(currentEntry);
            currentEntry = parent;
        }

        return currentEntry;
    }

    @GetMapping("{dictName}/{entryName}/parentword")
    public Entry getParentWordOnly(@PathVariable String dictName, @PathVariable String entryName) {

        return entryRepository.getParentWordOnly(dictName, entryName);
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public void delete(@RequestBody Map<String, Long> params){
        entryRepository.deleteEntryById(params.get("id"));
    }

    @GetMapping("{dictName}/{entryName}/testfind")
    public Entry testfind(@PathVariable String dictName, @PathVariable String entryName) {

        return entryRepository.findByWordNoRelationships(dictName, entryName);
    }

}
