package dto;

public class SearchPageResult implements Comparable<SearchPageResult>{

    private String uri;
    private String title;
    private double relevance;

    public SearchPageResult() {
    }

    public SearchPageResult(String uri, String title, double relevance) {
        this.uri = uri;
        this.title = title;
        this.relevance = relevance;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    @Override
    public int compareTo(SearchPageResult otherSearchResult) {
        if (this.relevance < otherSearchResult.getRelevance()) {
            return -1;
        } else if (this.relevance > otherSearchResult.getRelevance()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return getUri() + " || " + getTitle() + "\n" +
                getRelevance();
    }
}
