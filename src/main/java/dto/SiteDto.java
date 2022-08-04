package dto;

import java.util.Objects;

public class SiteDto {
    private Integer id;
    private String name;
    private String url;

    public SiteDto() {
    }

    public SiteDto(String url) {
        this.url = url;
    }

    public SiteDto(String url, String name) {
        this.url = url;
        this.name = name;
        this.id = hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteDto siteDto = (SiteDto) o;
        return Objects.equals(name, siteDto.name) && Objects.equals(url, siteDto.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
