package utils;

import dto.LemmaDto;
import entity.Lemma;
import entity.PageEntity;
import search_engine.Page;

public class MappingUtils {

    public static PageEntity pageToPageEntity(Page page) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPath(page.getUrl());
        pageEntity.setCode(page.getStatusCode());
        pageEntity.setContent(page.getHtml());
        pageEntity.setId(page.getId());
        pageEntity.setSiteId(page.getSiteId());
        return pageEntity;
    }

    public static Page pageEntityToPageDto(PageEntity pageEntity) {
        Page page = new Page(pageEntity.getPath(), pageEntity.getContent(),
                pageEntity.getCode(), pageEntity.getSiteId());
        return page;
    }

    public static Lemma lemmaDtoToLemma(LemmaDto lemmaDto) {
        Lemma lemma = new Lemma();
        lemma.setLemma(lemmaDto.getLemma());
        lemma.setId(lemmaDto.getId());
        lemma.setFrequency(lemmaDto.getFrequency());
        return lemma;
    }

}
