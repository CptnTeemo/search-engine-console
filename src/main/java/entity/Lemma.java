package entity;

import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "lemma"
//        ,
//        indexes = {
//                @javax.persistence.Index(name = "LEMMA_IDX0", columnList = "lemma, site_id", unique = true) },
//        uniqueConstraints = {@UniqueConstraint(name = "UniqueLemmaAndSiteId", columnNames = {"lemma", "site_id"})}
)
@SQLInsert(sql="INSERT INTO lemma(frequency, lemma, site_id, id) VALUES (?, ?, ?, ?)" +
        "ON DUPLICATE KEY UPDATE frequency = frequency + 1")
public class Lemma implements Comparable<Lemma>{

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;
    @Column(name = "lemma", nullable = false)
    private String lemma;
    @Column(name = "frequency", nullable = false)
    private Integer frequency;
    @Column(name = "site_id")
    private Integer siteId;
    @OneToMany(mappedBy="lemma", cascade =
            CascadeType.ALL,
//            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private Set<IndexEntity> indexes = new HashSet<>();

    public Lemma() {
    }

    public Lemma(String lemma, Integer frequency, Integer siteId) {
        this.lemma = lemma;
        this.siteId = siteId;
        this.frequency = frequency;
        this.id = hashCode();
    }

    public Lemma(String lemma, Integer frequency) {
        this.lemma = lemma;
        this.frequency = frequency;
        this.id = hashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Set<IndexEntity> getIndexes() {
        return indexes;
    }

    public void setIndexes(Set<IndexEntity> indexes) {
        this.indexes = indexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lemma lemma1 = (Lemma) o;
        return siteId.equals(lemma1.siteId) &&
                Objects.equals(lemma, lemma1.lemma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lemma, siteId);
    }

    @Override
    public int compareTo(Lemma otherLemma) {
        if (this.frequency < otherLemma.getFrequency()) {
            return -1;
        } else if (this.frequency > otherLemma.getFrequency()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Лемма: " + getLemma()
//                + ", частота: " + getFrequency() + "\n" +
//                "страницы: " + getIndexes().size() + "\n" +
//                "путь страницы 0: " + getIndexes().size()
                ;
    }
}
