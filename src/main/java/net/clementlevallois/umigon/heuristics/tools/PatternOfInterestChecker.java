/*
 * To change this template, choose Toolc | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics.tools;

import com.vdurmont.emoji.EmojiParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.PatternOfInterest;
import net.clementlevallois.umigon.model.TypeOfTextFragment;

/*
 *
 * @author C. Levallois
 */
public class PatternOfInterestChecker {

    private static List<PatternOfInterest> patternsOfInterest = new ArrayList();

    public static void loadPatternsOfInterest() throws IOException {

        try ( // we load the patterns of interest
                 InputStream inputStream = PlaceHolderMULTI.class.getResourceAsStream("patterns_of_interest.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            List<String> patternsOfInterestAsTSV = br.lines().collect(Collectors.toList());
            PatternOfInterest poi;
            int i = 0;
            for (String patternOfInterestAsTSV : patternsOfInterestAsTSV) {
                if (i++ == 0) {
                    continue;
                }
                String[] elements = patternOfInterestAsTSV.split("\t");
                poi = new PatternOfInterest();
                poi.setDescription(elements[0]);
                poi.setRegex(elements[1]);
                poi.setShouldApplyToLowerCaseText(Boolean.valueOf(elements[2]));
                List<String> categoriesIds = Arrays.asList(elements[3].split(","));
                for (String categoryId : categoriesIds) {
                    Category category = new Category(categoryId);
                    poi.getCategories().add(category);
                }
                poi.setTypeOfTextFragment(elements[4]);
                patternsOfInterest.add(poi);
            }
        }
    }

    public static String containsPercentage(String text) {
        //do we find a percentage?
        boolean res = text.matches(".*\\d%.*");
        if (res) {
            //if so, is it followed by "off"?
            res = (text.toLowerCase().matches(".*\\d% off.*") | text.toLowerCase().matches(".*\\d% cash back.*"));
            if (res) {
                return "611";
            } else {
                return "621";
            }
        }
        return null;
    }

    public static List<ResultOneHeuristics> containsOnomatopaesOrAsciiEmoticons(String text) {
        List<ResultOneHeuristics> cats = new ArrayList();

        int index;

        Matcher matcher;
        for (PatternOfInterest poiLoop : patternsOfInterest) {
            matcher = poiLoop.getPattern().matcher(text);
            if (matcher.find()) {
                String termMatched = matcher.group();
                index = text.indexOf(termMatched);
                for (Category cat : poiLoop.getCategories()) {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(cat.getCategoryEnum(), index, termMatched.trim(), poiLoop.getTypeOfTextFragment());
                    resultOneHeuristics.setTypeOfTextFragment(poiLoop.getTypeOfTextFragment());
                    cats.add(resultOneHeuristics);
                }
            }
        }

        return cats;
    }    
    

//    public void containsTimeIndication() {
//        Heuristic heuristic;
//        TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics();
//        int index = 0;
//        for (String term : HeuristicsLoaderAtStartup.getMapH4().keySet()) {
//            if (lc.contains(term)) {
//                heuristic = HeuristicsLoaderAtStartup.getMapH12().get(term);
//                String result = termLevelHeuristics.checkFeatures(heuristic, lc, lc, term);
//                if (result != null) {
//                    index = lc.indexOf(term);
//                    tweet.addToListCategories(result, index);
//                }
//            }
//        }
//    }
    public static List<ResultOneHeuristics> containsAffectiveEmojis(String text) {
        int index;
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        String textWithEmojisAsAliases = EmojiParser.parseToAliases(text);
        long countSemiColon = textWithEmojisAsAliases.chars().filter(ch -> ch == ':').count();
        if (countSemiColon < 2) {
            return resultsHeuristics;
        }
        String[] terms = textWithEmojisAsAliases.split(":");
        for (String term : terms) {
            index = textWithEmojisAsAliases.indexOf(term);
            term = ":" + term + ":";
            String emojiBackToUnicode = EmojiParser.parseToUnicode(term);
            ResultOneHeuristics resultOneHeuristics = null;
            if (EmojisHeuristicsandResourcesLoader.getSetNegativeEmojis().contains(term)) {
                resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, index, emojiBackToUnicode, TypeOfTextFragment.TypeOfTextFragmentEnum.EMOJI);
            } else if (EmojisHeuristicsandResourcesLoader.getSetPositiveEmojis().contains(term)) {
                resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, index, emojiBackToUnicode, TypeOfTextFragment.TypeOfTextFragmentEnum.EMOJI);
            } else if (EmojisHeuristicsandResourcesLoader.getSetHyperSatisfactionEmojis().contains(term)) {
                resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._17, index, emojiBackToUnicode, TypeOfTextFragment.TypeOfTextFragmentEnum.EMOJI);
            }
            if (resultOneHeuristics != null) {
                resultsHeuristics.add(resultOneHeuristics);
            }
        }

        return resultsHeuristics;

    }
}
