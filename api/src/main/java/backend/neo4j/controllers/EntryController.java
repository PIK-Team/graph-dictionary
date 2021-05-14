package backend.neo4j.controllers;

import backend.neo4j.entities.Dictionary;
import backend.neo4j.entities.Entry;
import backend.neo4j.repositories.DictionaryRepository;
import backend.neo4j.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("entries2")
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
    public void define(@RequestBody Map<String, String> params) {
        if (params.get("parent_entry") != null)
            entryRepository.defineChildEntry(params.get("word"), params.get("definition"), Long.parseLong(params.get("parent_entry")));
        else
            entryRepository.defineRootEntry(params.get("word"), params.get("definition"));
    }

    @GetMapping
    public Collection<Entry> getAll() {
        return entryRepository.findAll();
    }


    @GetMapping("{id}/related")
    public Collection<Entry> getRelated(@PathVariable Long id){
        return entryRepository.getRelated(id);
    }

    @GetMapping("/findall")
    public Collection<Entry> myFindAll(){
        return entryRepository.myFindAll();
    }

}
