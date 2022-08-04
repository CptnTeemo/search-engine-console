package entity;

import javax.persistence.*;

@Entity
@Table(name = "field")
public class Field {

    private Integer id;
    private String name;
    private String selector;
    private float weight;

    public Field(String name, String selector, float weight) {
        this.name = name;
        this.selector = selector;
        this.weight = weight;
    }

    public Field() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "selector", nullable = false)
    public String getSelector() {
        return selector;
    }

    @Column(name = "weight", nullable = false)
    public float getWeight() {
        return weight;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return selector + " " + weight;
    }
}
