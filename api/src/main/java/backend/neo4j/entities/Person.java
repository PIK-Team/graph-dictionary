package backend.neo4j.entities;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node
public class Person {

    @Id
    @GeneratedValue
    private Long ID;
    private Long identity;
    private String name;
    private Integer born;
    private double degree;
    private Integer louvain;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }


    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    public Integer getLouvain() {
        return louvain;
    }

    public void setLouvain(Integer louvain) {
        this.louvain = louvain;
    }
}