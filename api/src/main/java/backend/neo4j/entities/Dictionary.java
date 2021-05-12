package backend.neo4j.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
public class Dictionary
{
    @Id
    @GeneratedValue
    private Long id;

    private String dictionaryName;

    @Relationship(type="INCLUDES")
    private List<Entry> entries = new ArrayList<>();

    public Dictionary(String dictionaryName, List<Entry> entries)
    {
        this.dictionaryName = dictionaryName;
        this.entries = entries;
    }

    public String getDictionaryName()
    {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName)
    {
        this.dictionaryName = dictionaryName;
    }

    public List<Entry> getEntries()
    {
        return entries;
    }

    public void setEntries(List<Entry> entries)
    {
        this.entries = entries;
    }
}