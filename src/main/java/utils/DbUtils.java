package utils;

import entity.Field;
import entity.IndexEntity;
import entity.Lemma;
import entity.PageEntity;
import morphology.LemmaCollection;
import morphology.Lemmatizer;
import org.hibernate.Session;
import search_engine.Page;

import java.io.IOException;
import java.util.*;

public class DbUtils {

    public static Session session;

    public DbUtils() {
    }

    public DbUtils(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static void pageWriter(Set<Page> results){
                results
//                .stream()
//                .limit(1)
                .forEach(e -> {
                    PageEntity pageEntity = MappingUtils.pageToPageEntity(e);
                    session.persist(pageEntity);
                });
        session.flush();
        session.clear();
    }

    public static void lemmaAndIndexWriter(Map<String, LemmaCollection> lemmaMap, Field titleField,
                                           Field bodyField){
        lemmaMap.forEach((key, value) -> {
            value.getTitle().forEach((tKey, tValue) ->{
                Lemma lemma = new Lemma(tKey, 1, value.getSiteId());
                IndexEntity indexEntity = new IndexEntity(value.getPageId(), lemma.getId(),
                        tValue * titleField.getWeight());
                session.save(indexEntity);
                session.save(lemma);
            });
            session.flush();
            session.clear();
            value.getBody().forEach((bKey, bValue) -> {
                Lemma lemma = new Lemma(bKey, 1, value.getSiteId());
                IndexEntity indexEntity = new IndexEntity(value.getPageId(), lemma.getId(),
                        bValue * bodyField.getWeight());
                session.save(indexEntity);
                session.save(lemma);
            });
            session.flush();
            session.clear();
        });
    }

//    public static void lemmaAndIndexWriter(Map<String, LemmaCollection> lemmaMap, Field titleField,
//                                           Field bodyField){
//        lemmaMap.forEach((key, value) -> {
//            value.getTitle().forEach((tKey, tValue) ->{
//                Lemma lemma = new Lemma(tKey, 1);
//                IndexEntity indexEntity = new IndexEntity(tKey,
//                        tValue * titleField.getWeight());
//                session.save(indexEntity);
//                session.save(lemma);
//            });
//            session.flush();
//            session.clear();
//            value.getBody().forEach((bKey, bValue) -> {
//                Lemma lemma = new Lemma(bKey, 1);
//                IndexEntity indexEntity = new IndexEntity(bKey,
//                        bValue * bodyField.getWeight());
//                session.save(indexEntity);
//                session.save(lemma);
//            });
//            session.flush();
//            session.clear();
//        });
//    }

    public static List<IndexEntity> searchIndexEntities(String input) throws IOException {
        List<Lemma> lemmaEntityList = new ArrayList<>();
        List<IndexEntity> indexEntityList = new ArrayList<>();
        Map<String, Long> lemmaCollection = Lemmatizer.uniqueWordCounter(input);
        lemmaCollection.forEach((key, value) -> {
            Lemma queryLemma = new Lemma(key, 1);
            String hql = "From " + IndexEntity.class.getSimpleName() + " Where rank_rate < 30 and" +
                    " lemma_id = " + queryLemma.getId();

            List<IndexEntity> lemmaHql = session.createQuery(hql).getResultList();
            indexEntityList.addAll(lemmaHql);
        });
        return indexEntityList;
    }

    public static List<Lemma> searchLemmas(String input) throws IOException {
        List<Lemma> lemmaEntityList = new ArrayList<>();
        Map<String, Long> lemmaCollection = Lemmatizer.uniqueWordCounter(input);
        lemmaCollection.forEach((key, value) -> {
            Lemma queryLemma = new Lemma(key, 1);
            String hql = "From " + Lemma.class.getSimpleName() + " Where " +
//                    "frequency < 30 and " +
                    "id = " + queryLemma.getId() +
                    " order by frequency";
            List<Lemma> lemmaHql = session.createQuery(hql).getResultList();
            lemmaEntityList.addAll(lemmaHql);
        });
        return lemmaEntityList;
    }

    public static List<PageEntity> getPageEntitiesFromDb(List<Page> pageList) {
        List<PageEntity> resultList = new ArrayList<>();
        pageList.forEach(e -> {
            String hql = "From " + PageEntity.class.getSimpleName() + " Where " +
                    "id = " + e.getId();
            List<PageEntity> pagesHql = session.createQuery(hql).getResultList();
            resultList.addAll(pagesHql);
        });
        return resultList;
    }

}
