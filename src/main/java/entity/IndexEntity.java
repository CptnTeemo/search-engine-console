package entity;

import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "index_table")
@SQLInsert(sql="INSERT INTO index_table(lemma_id, page_id, rank_rate, id) VALUES (?, ?, ?, ?)" +
        "ON DUPLICATE KEY UPDATE rank_rate = rank_rate + VALUES(rank_rate)")
public class IndexEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    @ManyToOne(cascade =
            CascadeType.ALL
//            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name="page_id", referencedColumnName = "id", nullable = true, insertable=false, updatable=false)
    private PageEntity pageEntity;

    @ManyToOne(cascade =
            CascadeType.ALL
//            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name="lemma_id", referencedColumnName = "id", nullable = true, insertable=false, updatable=false)
    private Lemma lemma;

    @Column(name = "page_id", nullable = false)
    private Integer pageId;
    @Column(name = "lemma_id", nullable = false)
    private Integer lemmaId;
    @Column(name = "rank_rate", nullable = false)
    private Float rank;


    public IndexEntity() {
    }

    public IndexEntity(Integer pageId, Integer lemmaId, Float rank) {
        this.pageId = pageId;
        this.lemmaId = lemmaId;
        this.rank = rank;
        this.id = hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getLemmaId() {
        return lemmaId;
    }

    public void setLemmaId(Integer lemmaId) {
        this.lemmaId = lemmaId;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public PageEntity getPageEntity() {
        return pageEntity;
    }

    public void setPageEntity(PageEntity pageEntity) {
        this.pageEntity = pageEntity;
    }

    public Lemma getLemma() {
        return lemma;
    }

    public void setLemma(Lemma lemma) {
        this.lemma = lemma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexEntity that = (IndexEntity) o;
        return Objects.equals(pageId, that.pageId) && Objects.equals(lemmaId, that.lemmaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageId, lemmaId);
    }

}
