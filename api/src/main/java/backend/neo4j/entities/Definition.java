package backend.neo4j.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

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
}