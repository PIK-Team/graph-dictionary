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
    private String imageURI;
    private String description;

    @Relationship(type="INCLUDES")
    private List<Entry> entries = new ArrayList<>();

    public Dictionary(String dictionaryName, String imageURI, String description, List<Entry> entries)
    {
        this.dictionaryName = dictionaryName;
        this.imageURI = imageURI;
        this.description = description;
        this.entries = entries;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public String getImageURI() { return imageURI; }

    public void setImageURI(String imageURI) { this.imageURI = imageURI; }
}