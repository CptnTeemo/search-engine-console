package morphology;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import search_engine.Page;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lemmatizer {

    //    private static Set<WebSiteElementInfo> siteElementInfoSet;
    private static volatile Map<String, LemmaCollection> lemmaSet = new HashMap<>();

//    public Set<WebSiteElementInfo> getSiteElementInfoSet() {
//        return siteElementInfoSet;
//    }

    public Map<String, LemmaCollection> getLemmaSet() {
        return lemmaSet;
    }

//    public void setSiteElementInfoSet(Set<WebSiteElementInfo> siteElementInfoSet) {
//        this.siteElementInfoSet = siteElementInfoSet;
//    }

    public void setLemmaSet(Map<String, LemmaCollection> lemmaSet) {
        this.lemmaSet = lemmaSet;
    }

    public static Map<String, LemmaCollection> getLemmaCollections(Set<Page> set) {
        set.forEach(e -> {
            Thread thread = new Thread(() -> {
            try {
                LemmaCollection lemma = new LemmaCollection(uniqueWordCounter(getTitleText(e.getHtml())),
                        uniqueWordCounter(getBodyText(e.getHtml())), e.getId(), e.getSiteId());
                lemmaSet.put(e.getUrl(), lemma);
                if (lemmaSet.size() % 25 == 0) {
                    System.out.println("Lemma was add");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        return lemmaSet;
    }

    public static Map<String, Long> uniqueWordCounter(String text) throws IOException {
        LuceneMorphology luceneMorph =
                new RussianLuceneMorphology();
        List<String> words = Arrays.asList(text.toLowerCase(Locale.ROOT).split("[^А-яЁё]"));
        List<String> uniqueWords = new ArrayList<>();
        words.forEach(e -> {
            if (!e.isEmpty()) {
                List<String> wordBaseForms =
                        luceneMorph.getMorphInfo(e);
                wordBaseForms.forEach(elem -> {
                    if (!elem.contains("ПРЕДЛ") && !elem.contains("СОЮЗ") &&
                            !elem.contains("МЕЖД") && !elem.contains("ЧАСТ")) {
//                        System.out.println(elem);
                        uniqueWords.add(elem.replaceAll("([А-я]+)(|)(.*)", "$1"));
                    }
                });
            }
        });
        return countDuplicates(uniqueWords);
    }

    public static Map<String, Long> countDuplicates(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    public static String getBodyText(String html) {
        Document htmlContent = Jsoup.parse(html);
        return htmlContent.select("body").text();
    }

    public static String getTitleText(String html) {
        Document htmlContent = Jsoup.parse(html);
        return htmlContent.select("title").text();
    }

}
