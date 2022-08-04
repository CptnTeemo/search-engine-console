package utils;

import dto.SearchPageResult;
import entity.IndexEntity;
import entity.Lemma;
import entity.PageEntity;
import morphology.Lemmatizer;
import search_engine.Page;

import java.util.*;

public class SearchUtils {

    public static void searchPagesFromLemmas(List<Lemma> lemmaList) {
        Set<IndexEntity> indexesResultFromLemmas = lemmaList.get(0).getIndexes();
        List<PageEntity> pageEntitiesResult = new ArrayList<>();
        List<Page> pageDtoList = getPageDtoList(indexesResultFromLemmas);
        List<Page> pageDtoResult = new ArrayList<>();
        for (int i = 1; i < lemmaList.size(); i++) {
            String lemma = lemmaList.get(i).getLemma();
            pageDtoResult.addAll(isLemmaContained(pageDtoList, lemma));
        }
//        pageEntitiesResult = getPageEntityList(pageDtoResult);
        pageEntitiesResult = DbUtils.getPageEntitiesFromDb(pageDtoResult);
        Map<PageEntity, Float> absoluteRelevance = calculateAbsoluteRelevanceIndexes(pageEntitiesResult);
//        absoluteRelevance.forEach((key, value) -> {
//            System.out.println(key + " " + value);
//        });
        List<SearchPageResult> searchResultSet = getSearchPageResult(absoluteRelevance);
        searchResultSet.sort(Collections.reverseOrder());
        searchResultSet.forEach(System.out::println);

//        Set<IndexEntity> indexEntitiesResult = pageEntitiesResult.get(0).getIndexes();

//        System.out.println(pageDtoResult.size());
//        System.out.println(pageEntitiesResult.size());
//        System.out.println(indexEntitiesResult.size());
//        indexEntitiesResult.forEach(e -> {
//            System.out.println(e.getLemma());
//        });
//        pageDtoResult.forEach(e -> {
//            System.out.println(e.getUrl());
//        });
//        pageEntitiesResult.forEach(e -> {
//            System.out.println(e.getPath());
//        });
    }

    public static List<Page> getPageDtoList(Set<IndexEntity> indexEntitySet) {
        List<Page> pageList = new ArrayList<>();
        indexEntitySet.forEach(e -> {
            pageList.add(MappingUtils.pageEntityToPageDto(e.getPageEntity()));
        });
        return pageList;
    }

    public static List<PageEntity> getPageEntityList(List<Page> pageList) {
        List<PageEntity> pageEntityListList = new ArrayList<>();
        pageList.forEach(e -> {
            pageEntityListList.add(MappingUtils.pageToPageEntity(e));
        });
        return pageEntityListList;
    }

    public static List<Page> isLemmaContained(List<Page> pageDtoList, String lemma) {
//        List<Page> tempPagesList = new ArrayList<>();
//        pageDtoList.forEach(e -> {
//            if (e.getHtml().contains(lemma)) {
//                tempPagesList.add(e);
//            }
//        });
        pageDtoList.removeIf(nextPage -> !nextPage.getHtml().contains(lemma));
        return pageDtoList;
    }

    public static Map<PageEntity, Float> calculateAbsoluteRelevanceIndexes(List<PageEntity> pageEntityList) {
        Map<PageEntity, Float> calculateAbsoluteRelevanceMap = new HashMap<>();
        pageEntityList.forEach(e -> {
            Set<IndexEntity> indexEntitiesResult = e.getIndexes();
            float rankSum = (float) indexEntitiesResult.stream()
                    .mapToDouble(IndexEntity::getRank).sum();
            calculateAbsoluteRelevanceMap.put(e, rankSum);
        });
        float maxRelevance = Collections.max(calculateAbsoluteRelevanceMap.values());
        calculateAbsoluteRelevanceMap.forEach((key, value) -> {
            calculateAbsoluteRelevanceMap.replace(key, value / maxRelevance);
        });
        return calculateAbsoluteRelevanceMap;
    }

    public static List<SearchPageResult> getSearchPageResult(Map<PageEntity, Float> pageEntityMap) {
        List<SearchPageResult> searchPageResults = new ArrayList<>();
        pageEntityMap.forEach((key, value) -> {
            searchPageResults.add(new SearchPageResult(key.getPath(),
                    Lemmatizer.getTitleText(key.getContent()), value));
        });
        return searchPageResults;
    }

    public static List<PageEntity> isLemmaContained(Set<IndexEntity> indexEntitySet, String lemma) {
        List<PageEntity> tempPageList = new ArrayList<>();
        Set<IndexEntity> tempIndexesSet = new HashSet<>();
        indexEntitySet.forEach(e -> {
            String pageContent = e.getPageEntity().getContent();
            if (pageContent.contains(lemma)) {
                tempPageList.add(e.getPageEntity());
                tempIndexesSet.add(e);
            }
        });
        return tempPageList;
    }

}
