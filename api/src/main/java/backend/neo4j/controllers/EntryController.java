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
     * Add an entry do a dictionary
     * Words need to be unique in a dictionary, so trying to add an already existing word will result in an error.
     * Called like this:
     * curl -i -X POST -H "Content-Type:application/json" -d '{"word" : "myword", "definition": "mydef", "dictionary": "mydict"}' http://localhost:9090/entries/define
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

    /**
     * Add definition to an existing entry
     * @param params  {"word" : "myword", "definition": "mydef", "dictionary": "mydict"}
     * @return : The entry modified and all it's definitions, including the one inserted
     */
    @RequestMapping(value = "/adddef", method = RequestMethod.POST)
    @ResponseBody
    public List<Entry> addDefinition(@RequestBody Map<String, String> params) {

        String word_param = "word";
        String dictionary_param = "dictionary";
        String definition_param = "definition";
        return entryRepository.addDefinition(params.get(word_param), params.get(definition_param), params.get(dictionary_param));

    }



    @GetMapping
    public Collection<Entry> getAll() {
        return entryRepository.findAll();
    }


    /**
     * Returns entries that share the same parent entry with the entry identified by word
     * (the specified entry isn't included in the returned collection).
     */
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

    /**
     * Return all entries on the path to the root entry from the specified entry,
     * plus all children of the specified entry,
     * plus all definitions of the specified entry.
     */
//    @GetMapping("{dictName}/{entryName}/overview")
//    @RequestMapping(
//            value = "{dictName}/{entryName}/overview",
//            params = "limit",
//            method = RequestMethod.GET
//    )
    @GetMapping(value = "{dictName}/{entryName}/overview")
    public Entry entryOverview(@PathVariable String dictName, @PathVariable String entryName,
                               @RequestParam(name = "child-limit", required = false, defaultValue = "100") int child_limit,
                               @RequestParam(name = "parent-limit", required = false, defaultValue = "10") int parent_limit) {

        Entry currentEntry;
        Entry parent;

        System.out.println("Limit: " + child_limit);
        List<Entry> children = entryRepository.getChildrenWordsOnly(dictName, entryName, child_limit);
        currentEntry = findByWord(entryName, dictName);
        currentEntry.setSubentries(children);

        while (parent_limit > 0 && (parent = entryRepository.getParentWordOnly(dictName, currentEntry.getWord().getWord())) != null ){
            parent.addEntry(currentEntry);
            currentEntry = parent;
            --parent_limit;
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
