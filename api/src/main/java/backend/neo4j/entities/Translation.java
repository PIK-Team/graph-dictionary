package backend.neo4j.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Set;

@RelationshipProperties
public class Translation {

    @Id
    @GeneratedValue
    private Long id;

    private final String language;

    @TargetNode
    private final Word translation;

    public Translation(String language, Word translation) {
        this.language = language;
        this.translation = translation;
    }

    public String getLanguage() {
        return language;
    }

    public Word getTranslation() {
        return translation;
    }
}
