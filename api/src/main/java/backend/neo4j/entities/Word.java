package backend.neo4j.entities;

import java.util.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Word
{
    @Id
    @GeneratedValue
    private long ID;
    private String word;

    @Relationship(type="MEANS")
    private List<Definition> definitions = new ArrayList<>();

    public Word(String word, List<Definition> definitions)
    {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord()
    {
        return word;
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public List<Definition> getDefinitions()
    {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions)
    {
        this.definitions = definitions;
    }
}