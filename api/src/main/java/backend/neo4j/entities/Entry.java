package backend.neo4j.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
public class Entry
{
    @Id @GeneratedValue
    private Long id;

    @Relationship(type="DEFINES")
    private Word word;

    @Relationship(type="MEANS")
    private List<Definition> defiitions = new ArrayList<>();

    public Entry(Word word, List<Definition> defiitions)
    {
        this.word = word;
        this.defiitions = defiitions;
    }

    public Word getWord()
    {
        return word;
    }

    public void setWord(Word word)
    {
        this.word = word;
    }

    public List<Definition> getDefiitions()
    {
        return defiitions;
    }

    public void setDefiitions(List<Definition> defiitions)
    {
        this.defiitions = defiitions;
    }
}