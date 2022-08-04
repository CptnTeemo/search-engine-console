package dto;

import entity.Lemma;

import java.util.Objects;

public class LemmaDto implements Comparable<LemmaDto>{
    private int id;
    private String lemma;
    private int frequency;
    private int pageId;

    public LemmaDto() {
    }

    public LemmaDto(int id, String lemma, int frequency, int pageId) {
        this.id = id;
        this.lemma = lemma;
        this.frequency = frequency;
        this.pageId = pageId;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LemmaDto lemmaDto = (LemmaDto) o;
        return Objects.equals(lemma, lemmaDto.lemma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lemma);
    }

    @Override
    public int compareTo(LemmaDto otherLemma) {
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
        return "Лемма: " + getLemma() + ", частота: " + getFrequency();
    }

}
