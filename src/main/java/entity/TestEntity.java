package entity;

import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "test_table")
@SQLInsert(sql="INSERT INTO test_table(frequency, lemma, site_id, id) VALUES (?, ?, ?, ?)" +
        "ON DUPLICATE KEY UPDATE frequency = frequency + 1")
public class TestEntity {

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

    public TestEntity() {
    }

    public TestEntity(String lemma, Integer frequency, Integer siteId) {
        this.lemma = lemma;
        this.frequency = frequency;
        this.siteId = siteId;
        this.id = hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return Objects.equals(lemma, that.lemma) && Objects.equals(siteId, that.siteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lemma, siteId);
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TestEntity that = (TestEntity) o;
//        return Objects.equals(lemma, that.lemma);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(lemma);
//    }
}
