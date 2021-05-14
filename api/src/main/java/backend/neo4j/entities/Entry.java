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

    @Relationship(type="DEFINES", direction = Relationship.Direction.OUTGOING)
    private Word word;

    @Relationship(type="MEANS", direction = Relationship.Direction.OUTGOING)
    private List<Definition> definitions = new ArrayList<>();

    @Relationship(type="CATEGORIZES", direction = Relationship.Direction.OUTGOING)
    private List<Entry> subentries = new ArrayList<>();


    public List<Entry> getSubentries()
    {
        return subentries;
    }

    public void setSubentries(List<Entry> subentries)
    {
        this.subentries = subentries;
    }

    public Word getWord()
    {
        return word;
    }

    public void setWord(Word word)
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