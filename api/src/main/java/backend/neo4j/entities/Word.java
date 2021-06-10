package backend.neo4j.entities;

import java.util.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Word
{

//    @Id @GeneratedValue
//    private Long id;
    @Id
    private String word;

    private static final Set<String> supportedLanguages = Set.of("english", "polish", "spanish", "german");

    public static Set<String> getSupportedLanguages() { return supportedLanguages; }

    public static boolean isSupportedLanguage(String lang) { return supportedLanguages.contains(lang.toLowerCase()); }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    @Relationship(type="TRANSLATES", direction = Relationship.Direction.OUTGOING)
    private List<Translation> translations = new ArrayList<>();

    public List<Translation> getTranslations() {
        return translations;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(getWord(), word1.getWord());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getWord());
    }


}