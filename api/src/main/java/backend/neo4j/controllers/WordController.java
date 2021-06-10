package backend.neo4j.controllers;


import backend.neo4j.entities.Definition;
import backend.neo4j.entities.Entry;
import backend.neo4j.entities.Word;
import backend.neo4j.repositories.DefinitionRepository;
import backend.neo4j.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/words")
public class WordController {

    @Autowired
    WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping("/get/{word}")
    public Word getWord (@PathVariable String word){
        return wordRepository.find(word);
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

//
//    @GetMapping("{word}/{dictionary}/findByWord")
//    public Entry findByWord(@PathVariable String word, @PathVariable String dictionary){
//        return entryRepository.findByWord(word, dictionary);
//    }


    /**
     * Define a word as a translation of another word, if the specified words are not in the database
     * they will be created by this method.
     * Expected request body: {word1 : "sausage", lang1: "english", word2: "wurst", lang2: "german"}
     * @return : A response, whose body includes information about any errors encountered
     */
    @PostMapping("/add-translation")
    public ResponseEntity<String > addTranslation(@RequestBody @Valid AddTranslationForm form, BindingResult result){

        if (result.hasErrors()) {
            return new ResponseEntity<>
                    ("Incorrect form structure, it should look like this+\n" +
                            "{word1 : \"sausage\", lang1: \"english\", word2: \"wurst\", lang2: \"german\"}",
                            HttpStatus.BAD_REQUEST);
        }

        if (!(Word.isSupportedLanguage(form.getLang1()) && Word.isSupportedLanguage(form.getLang2()))){
            return new ResponseEntity<>(
                    "Unsupported languages, the supported languages are: "+
                            Word.getSupportedLanguages().toString(),
                    HttpStatus.BAD_REQUEST);

        }

        wordRepository.addTranslation(form.getWord1(), form.getLang1(),form.getWord2(), form.getLang2());

        return ResponseEntity.ok("ok");

    }

    @GetMapping("/{word}/translations")
    public Map<String, String> getTranslations(@PathVariable String word){

        Word dbWord = wordRepository.find(word);

        Map<String, String> translations = dbWord.getTranslations().stream()
                .collect(Collectors.toMap(
                        trans -> trans.getLanguage(),
                        trans -> trans.getTranslation().getWord()
                ));
        return translations;
    }



    static class AddTranslationForm{
        @NonNull
        private String word1;
        @NonNull
        private String lang1;
        @NonNull
        private String word2;
        @NonNull
        private String lang2;

        @NonNull
        public String getWord1() {
            return word1;
        }

        public void setWord1(@NonNull String word1) {
            this.word1 = word1;
        }

        @NonNull
        public String getLang1() {
            return lang1;
        }

        public void setLang1(@NonNull String lang1) {
            this.lang1 = lang1;
        }

        @NonNull
        public String getWord2() {
            return word2;
        }

        public void setWord2(@NonNull String word2) {
            this.word2 = word2;
        }

        @NonNull
        public String getLang2() {
            return lang2;
        }

        public void setLang2(@NonNull String lang2) {
            this.lang2 = lang2;
        }
    }


}
