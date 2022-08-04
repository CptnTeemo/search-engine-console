package search_engine;

import dto.SiteDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class LinkCollector extends RecursiveTask<Set<Page>> {

    private String path;
    private String mainPath;
    private SiteDto siteDto;
    private Page page;
    private static Map<String, Page> pagesCollection = new HashMap<>();

    public LinkCollector(String path, SiteDto siteDto) {
        this.path = path;
        this.siteDto = siteDto;
    }

    public String getMainPath() {
        return mainPath;
    }

    public void setMainPath(String mainPath) {
        this.mainPath = mainPath;
    }

    @Override
    protected Set<Page> compute() {
        setMainPath(siteDto.getUrl());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Document document = null;
        if (!path.matches("(http|https)(.*)") & !path.contains(mainPath)) {
            return new TreeSet<>();
        }
        try {
            document = Jsoup.connect(path)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            Connection.Response response = Jsoup.connect(path)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .execute();

            if (document != null) {

                Elements linkList = document.select("a");
                List<LinkCollector> tasks = new ArrayList<>();
                Set<Page> siteMap = new TreeSet<>();
                Integer statusCode = response.statusCode();
                String getUrl = mainPath + "(.*)";
                Page page =
                        new Page(path.replaceAll(getUrl, "/"+"$1"), document.html(),
                                statusCode, siteDto.getId());

                pagesCollection.put(path, page);

                Set<String> hrefList = linksCollector(linkList, path);
                if (hrefList.size() > 0) {
                    hrefList.forEach(e -> {
                        LinkCollector newLinkCollector = new LinkCollector(e, siteDto);
                        newLinkCollector.fork();
                        tasks.add(newLinkCollector);
                    });
                }
                Set<Map.Entry<String, Page>> entrySet = pagesCollection.entrySet();
                entrySet.forEach(e -> siteMap.add(e.getValue()));

                addResultsFromTasks(siteMap, tasks);
                return new TreeSet<>(siteMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TreeSet<>();
    }


    private void addResultsFromTasks(Set<Page> list, List<LinkCollector> tasks) {
        for (LinkCollector item : tasks) {
            list.addAll(item.join());
        }
//        System.out.println("task size - " + tasks.size() + " list size - " + list.size());
    }

    public Set<String> linksCollector(Elements elements, String path) {
        Set<String> hrefList = new TreeSet<>();
//        System.out.println(path);
        elements.forEach(e -> {
//            System.out.println(e);
            String subPath = e.attr("abs:href");
//            System.out.println(subPath);
            if (subPath.contains(path) && !subPath.equals(path) &&
                    !subPath.contains("#") && !subPath.contains(".pdf")) {
                hrefList.add(subPath);
            }
        });
        return hrefList;
    }


//    @Override
//    protected List<WebSiteElementInfo> compute() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Document document = null;
//        try {
//            document = Jsoup.connect(path)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT" +
//                            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (document != null) {
//            Elements linkList = document.select("a");
////        Elements liList = linkList.select("a");
//            List<LinkCollector> tasks = new ArrayList<>();
////            List<String> siteMap = new ArrayList<>();
//            List<WebSiteElementInfo> siteMap = new ArrayList<>();
//            List<String> hrefList = linksCollector(linkList, path);
//            if (hrefList.size() > 1) {
//                hrefList.forEach(e -> {
//                    LinkCollector webSiteMapCreator = new LinkCollector(e);
//                    webSiteMapCreator.fork();
//                    tasks.add(webSiteMapCreator);
//                });
//                siteMap.add(new WebSiteElementInfo(path, document.html()));
////                siteMap.addAll(hrefList);
//            }
//
////            addResultsFromTasks(siteMap, tasks);
//            return new ArrayList<>(siteMap);
//        }
//        return new ArrayList<>();
//    }

//    private void addResultsFromTasks(List<WebSiteElementInfo> list, List<LinkCollector> tasks) {
//        for (LinkCollector item : tasks) {
//            list.addAll(item.join());
//        }
//    }

}
