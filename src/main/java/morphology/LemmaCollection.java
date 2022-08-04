package morphology;

import java.util.HashMap;
import java.util.Map;

public class LemmaCollection {
    Map<String, Long> title = new HashMap<>();
    Map<String, Long> body = new HashMap<>();
    public Integer pageId;
    private Integer siteId;

    public LemmaCollection(Map<String, Long> title, Map<String, Long> body, Integer pageId, Integer siteId) {
        this.title = title;
        this.body = body;
        this.pageId = pageId;
        this.siteId = siteId;
    }

    public LemmaCollection(Map<String, Long> title, Map<String, Long> body, Integer pageId) {
        this.title = title;
        this.body = body;
        this.pageId = pageId;
    }

    public Map<String, Long> getTitle() {
        return title;
    }

    public Map<String, Long> getBody() {
        return body;
    }

    public void setTitle(Map<String, Long> title) {
        this.title = title;
    }

    public void setBody(Map<String, Long> body) {
        this.body = body;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}
