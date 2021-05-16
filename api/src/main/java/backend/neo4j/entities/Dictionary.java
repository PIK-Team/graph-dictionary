package backend.neo4j.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Node
public class Dictionary
{
    @Id
    private String dictionaryName;
    private String imageURI;
    private String description;

    @Relationship(type="INCLUDES")
    private List<Entry> entries = new ArrayList<>();


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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(getDictionaryName(), that.getDictionaryName()) && Objects.equals(getImageURI(), that.getImageURI()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getEntries(), that.getEntries());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getDictionaryName(), getImageURI(), getDescription(), getEntries());
    }
}