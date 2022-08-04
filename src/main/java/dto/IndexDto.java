package dto;

public class IndexDto {

    private String url;
    private String lemma;
    private float rank;

    public IndexDto(String url, String lemma, float rank) {
        this.url = url;
        this.lemma = lemma;
        this.rank = rank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}
