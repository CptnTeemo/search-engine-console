package entity;

import dto.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "site_table")
public class SiteEntity {
    @Id
    @Column(nullable = false)
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum('INDEXING', 'INDEXED', 'FAILED')")
    private Status status;
    @Column(name = "status_time", nullable = false)
    private LocalDateTime statusTime;
    @Column(name = "last_error")
    private String lastError;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String name;

    public SiteEntity() {
    }

    public SiteEntity(Integer id, Status status, LocalDateTime statusTime, String lastError, String url, String name) {
        this.id = id;
        this.status = status;
        this.statusTime = statusTime;
        this.lastError = lastError;
        this.url = url;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public Enum getStatus() {
        return status;
    }

    public LocalDateTime getStatusTime() {
        return statusTime;
    }

    public String getLastError() {
        return lastError;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteEntity that = (SiteEntity) o;
        return Objects.equals(url, that.url) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, name);
    }
}
