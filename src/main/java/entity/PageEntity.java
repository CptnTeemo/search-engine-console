package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "page")
public class PageEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "path", nullable = false, /*unique = true,*/ columnDefinition = "VARCHAR(500)")
    private String path;
    @Column(name = "code", nullable = false)
    private Integer code;
    @Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;
    @Column(name = "site_id")
    private Integer siteId;
    @OneToMany(mappedBy="pageEntity", cascade =
            CascadeType.ALL,
//            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
            fetch = FetchType.LAZY) //TODO: выбрать вариант, проверить Lazy
    private Set<IndexEntity> indexes = new HashSet<>();

    public PageEntity() {
    }

    public PageEntity(String path, Integer code, String content) {
        this.path = path;
        this.code = code;
        this.content = content;
        this.id = hashCode();
    }

    public PageEntity(String path, Integer code, String content, Integer siteId) {
        this.path = path;
        this.code = code;
        this.content = content;
        this.siteId = siteId;
        this.id = hashCode();
    }

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Integer getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setContent(String content) {
        this.content = content;
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
        PageEntity that = (PageEntity) o;
        return Objects.equals(path, that.path) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, content);
    }

    @Override
    public String toString() {
        return getPath();
    }
}
