package search_engine;

import dbconnection.ConnectionToDataBase;
import dbconnection.DBConnection;
import dto.SiteDto;
import entity.*;
import morphology.LemmaCollection;
import morphology.Lemmatizer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.DbUtils;
import utils.SearchUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

    //        private static final String SOURCE = "https://ipfran.ru/";
    private static final String SOURCE = "http://www.playback.ru/";
//        private static final String SOURCE = "https://secure-headland-59304.herokuapp.com/";
    private static final String SITE_NAME = "Телефоны";
//    private static final String SITE_NAME = "Не телефоны";

    public static int BATCH_SIZE = 50;


    public static void main(String[] args) throws IOException, SQLException {
        long start = System.currentTimeMillis();
        DBConnection.getConnection();
        Session session = ConnectionToDataBase.getSession();
        Transaction transaction = ConnectionToDataBase.getTransaction();

        //TODO: тестовый прогон
//        testWriteInTestTable(session);

        Field titleField = session.get(Field.class, 1);
        Field bodyField = session.get(Field.class, 2);

//      TODO: основная часть
        Set<Page> results = pageCollector();
        System.out.println("Task complete");

//      TODO: лемматизация
        Map<String, LemmaCollection> lemmaMap = Lemmatizer.getLemmaCollections(results);

//      TODO: запись страниц, лемм и индексация
        DbUtils dbUtils = new DbUtils(session);
        dbUtils.pageWriter(results);
        dbUtils.lemmaAndIndexWriter(lemmaMap, titleField, bodyField);

//      TODO: поиск в БД
//        searchIndexDataBase(session, dbUtils);

//        Document document = null;
//        Document document2 = null;
//        try {
//            document = Jsoup.connect(SOURCE)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
//                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get();
//            Connection.Response response = Jsoup.connect(SOURCE)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
//                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .execute();
//            System.out.println(response.statusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Set<String> anotherSet = new TreeSet<>();
//        if (document != null) {
//            Element text = document.select("title").first();
//            Element body = document.select("body").first();
//            System.out.println(text.text());
//            System.out.println(body.text());
//            String html = document.html();
//            System.out.println(html);
//            document2 = Jsoup.parse(html);
//            Elements body2 = document2.select("body");
//            String body3 = document2.select("body").text();
//            System.out.println("=======");
//            System.out.println(document2.html());
//            System.out.println(body2.text());
//            System.out.println(body3);
//            System.out.println("==========");
//            Element link = document.select("a").first();
//            String relHref = link.attr("href"); // == "/"
//            System.out.println(relHref);
//            System.out.println(document.baseUri());
////            System.out.println(document.html());
//            String regex = SOURCE + "(/.*)";
//
//
//            Elements elements = document.select("a");
//            elements.forEach(e -> {
//                String changedUrl = e.attr("abs:href");
//                System.out.println(changedUrl.replaceAll(regex, "$1") + " Замена");
//                System.out.println(e.attr("href"));
//                anotherSet.add(e.attr("href"));
//                System.out.println("==================");
//            });
//        }
//        anotherSet.forEach(System.out::println);
        closeAllConnections();

        System.out.println(System.currentTimeMillis() - start + " ms");

    }

    public static Set<Page> pageCollector() {
        SiteDto siteDto = new SiteDto(SOURCE, SITE_NAME);
        ForkJoinPool pool = new ForkJoinPool();
        LinkCollector linkCollector = new LinkCollector(SOURCE, siteDto);
        pool.execute(linkCollector);
        do {
            System.out.print("******************************************\n");
            System.out.println("Task in progress");
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
            System.out.print("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!linkCollector.isDone());
        pool.shutdown();
        Set<Page> results = linkCollector.join();
        System.out.println(results.size());
        return results;
    }

    public static void searchIndexDataBase(Session session, DbUtils dbUtils) throws IOException {
//        Lemma testLemma = session.get(Lemma.class, 1952987339);
//        System.out.println(testLemma.getLemma() + " " + testLemma.getId());
//        Lemma newTestLemma = new Lemma("купить", 1);
//        System.out.println(newTestLemma.getId());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите запрос");
        String input = scanner.nextLine();
//        List<IndexEntity> lemmaQueryResult = dbUtils.searchIndexEntities(input.toLowerCase(Locale.ROOT));
        List<Lemma> lemmaQueryResult = dbUtils.searchLemmas(input.toLowerCase(Locale.ROOT));
        Collections.sort(lemmaQueryResult);
        if (!lemmaQueryResult.isEmpty()) {
            System.out.println(lemmaQueryResult.size());
            lemmaQueryResult.forEach(e -> {
                System.out.println(e.getLemma() + " " + e.getFrequency());
            });
            SearchUtils.searchPagesFromLemmas(lemmaQueryResult);

//            System.out.println(lemmaQueryResult.get(0).getLemma());
//            System.out.println(lemmaQueryResult.get(1).getPageEntity().getContent());
//            lemmaQueryResult.forEach(System.out::println);
//            Set<IndexEntity> testLemmaIndexes = lemmaQueryResult.get(1).getIndexes();
//            testLemmaIndexes.forEach(e -> System.out.println(e.getPageEntity().getPath()));
        } else {
            System.out.println("Something went wrong");
        }
    }

    public static void testWriteInTestTable(Session session) {
        for (int i = 0; i < 9; i++) {
            TestEntity testEntity = new TestEntity(i +"a", i, i*8);
            session.save(testEntity);
            session.flush();
            session.clear();
        }
        for (int i = 0; i < 5; i++) {
            TestEntity testEntity = new TestEntity(i +"a", i, i*8);
            session.save(testEntity);
            session.flush();
            session.clear();
        }
    }

    public static void closeAllConnections() throws SQLException {
        ConnectionToDataBase.setTransactionCommit();
        ConnectionToDataBase.closeSessionFactory();
        DBConnection.closeConnection();
    }

}
