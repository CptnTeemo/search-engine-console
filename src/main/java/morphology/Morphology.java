package morphology;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Morphology {

    public static void main(String[] args) throws IOException {
//        LuceneMorphology luceneMorph =
//                new RussianLuceneMorphology();
//        String text = "Повторное появление леопарда в Осетии позволяет предположить, что\n" +
//                "леопард леопарда не леопарду копать хитро хитрый копал" +
//                " постоянно обитает в некоторых районах и Северного Кавказа.\n";
//        List<String> words = Arrays.asList(text.toLowerCase(Locale.ROOT).split("[^А-яЁё]"));
//        List<String> uniqueWords = new ArrayList<>();
//        words.forEach(e -> {
//            if (!e.isEmpty()) {
//                List<String> wordBaseForms =
//                        luceneMorph.getMorphInfo(e);
//                wordBaseForms.forEach(elem -> {
//                    if (!elem.contains("ПРЕДЛ") && !elem.contains("СОЮЗ") &&
//                    !elem.contains("МЕЖД") && !elem.contains("ЧАСТ")) {
//                        System.out.println(elem);
//                        uniqueWords.add(elem.replaceAll("([А-я]+)(|)(.*)", "$1"));
//                    }
//                });
//            }
//        });
//
//
//
////        List<String> wordBaseForms =
////                luceneMorph.getNormalForms(text.toLowerCase(Locale.ROOT));
////        wordBaseForms.forEach(System.out::println);
//
////        LuceneMorphology luceneMorph =
////                new RussianLuceneMorphology();
////        List<String> wordBaseForms =
////                luceneMorph.getMorphInfo("илино");
////        wordBaseForms.forEach(e -> {
////            if (e.contains("СОЮЗ")) {
////                System.out.println("Ага");
////            } else {
////                System.out.println("Неа");
////            }
////            System.out.println(e);
////        });
//        Map<String, Long> frequency = countDuplicates(uniqueWords);
//        frequency.forEach((key, value) -> System.out.println(key + " - " + value));

        String text = "Повторное появление леопарда в Осетии позволяет предположить, " +
                "что леопард постоянно обитает в некоторых районах Северного Кавказа.";
        Lemmatizer lemmatizer = new Lemmatizer();
        Map<String, Long> uniqueWordMap = lemmatizer.uniqueWordCounter(text);
        uniqueWordMap.forEach((key, value) -> System.out.println(key + " - " + value));
    }

//    public static Map<String, Long> countDuplicates(List<String> inputList) {
//        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
//    }

}
