package backend.neo4j.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.stereotype.Indexed;

import java.util.Objects;

@Node
public class Definition
{
    @Id @GeneratedValue
    private Long id;

//    @JsonProperty("definition")
    private String definition;

//    public Definition(String definition)
//    {
//        this.definition = definition;
//    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String definition)
    {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return Objects.equals(id, that.id) && Objects.equals(getDefinition(), that.getDefinition());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, getDefinition());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}