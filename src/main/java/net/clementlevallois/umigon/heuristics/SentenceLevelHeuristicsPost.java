/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.model.Document;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import net.clementlevallois.umigon.model.CategoryAndIndex;
import net.clementlevallois.utils.StatusCleaner;

/**
 *
 * @author C. Levallois
 */
public class SentenceLevelHeuristicsPost {

    private String status;
    private String statusStripped;
    private Document tweet;
    private String lang;
    private final HeuristicsLoaderOnDemand heuristics;

    public SentenceLevelHeuristicsPost(HeuristicsLoaderOnDemand heuristics) {
        this.heuristics = heuristics;
    }

    public Document applyRules(Document tweet, String status, String statusStripped, String lang) {
        this.status = status;
        this.tweet = tweet;
        this.lang = lang;
        this.statusStripped = statusStripped;

//        containsMoreThan2Mentions();
        containsANegationAndAPositiveAndNegativeSentiment();
        containsModerator();
        isIronicallyPositive();
//        containsNegation();
        isStatusGarbled();
        whenAllElseFailed();
        return tweet;
    }

//    public void containsMoreThan2Mentions() {
//        int countArobase = StringUtils.countMatches(status, "@");
//        if (countArobase > 2 & !tweet.getListCategories().contains("12")) {
//            tweet.addToListCategories("061", -1);
//        }
//    }
    public void isIronicallyPositive() {
        if (tweet.getListCategories().contains("11") & tweet.getListCategories().contains("12")) {
            for (String term : heuristics.getSetIronicallyPositive(lang)) {
                if (status.contains(term)) {
                    tweet.deleteFromListCategories("12");
                }
            }
        }
    }

    public void containsNegation() {
        StatusCleaner cleaner = new StatusCleaner();
        status = cleaner.removeStartAndFinalApostrophs(status);
        status = cleaner.removePunctuationSigns(status).toLowerCase().trim();

        Set<String> termsInStatus = new HashSet();
        termsInStatus.addAll(Arrays.asList(status.split(" ")));
        if (!tweet.getListCategories().isEmpty()) {
            return;
        }
        for (String term : heuristics.getSetNegations(lang)) {
            if (termsInStatus.contains(term)) {
                tweet.addToListCategories("12", -1);
            }
        }
    }

    public void containsModerator() {
        if (tweet.getListCategories().isEmpty()) {
            return;
        }
        String statusStrippedLowerCase = statusStripped.toLowerCase();
        for (String term : heuristics.getSetModerators(lang)) {
            if (statusStrippedLowerCase.contains(term)) {
                int indexMod = statusStrippedLowerCase.indexOf(term);
                Queue<CategoryAndIndex> mapCategoriesToIndex = tweet.getMapCategoriesToIndex();
                Iterator<CategoryAndIndex> iterator = mapCategoriesToIndex.iterator();
                while (iterator.hasNext()) {
                    CategoryAndIndex next = iterator.next();
                    if (next.getIndex() > indexMod) {
                        iterator.remove();
                        tweet.getListCategories().remove(next.getCategory());
                    }
                }
            }
        }
    }

    public void containsANegationAndAPositiveAndNegativeSentiment() {
        Set<Integer> indexesPos = tweet.getAllIndexesForCategory("11");
        Set<Integer> indexesNeg = tweet.getAllIndexesForCategory("12");

        if (indexesPos.isEmpty() || indexesNeg.isEmpty()) {
            return;
        }
        int indexModerator;
        int indexPos = 0;
        int indexNeg = 0;

        Set<String> termsInStatus = new HashSet();
        termsInStatus.addAll(Arrays.asList(statusStripped.split(" ")));
        Iterator<Integer> iterator;

        iterator = indexesPos.iterator();
        while (iterator.hasNext()) {
            Integer currIndex = iterator.next();
            if (indexPos < currIndex) {
                indexPos = currIndex;
            }
        }
        iterator = indexesNeg.iterator();
        while (iterator.hasNext()) {
            Integer currIndex = iterator.next();
            if (indexNeg < currIndex) {
                indexNeg = currIndex;
            }
        }

        for (String term : heuristics.getSetNegations(lang)) {
            if (termsInStatus.contains(term)) {
                indexModerator = status.indexOf(term);
                if ((indexPos < indexModerator & indexNeg > indexModerator)) {
                    tweet.deleteFromListCategories("11");
                    break;
                } else if ((indexPos > indexModerator & indexNeg < indexModerator)) {
                    tweet.deleteFromListCategories("12");
                    break;
                }
                if ((indexModerator < indexPos & indexModerator < indexNeg & indexPos < indexNeg)) {
                    tweet.deleteFromListCategories("11");
                    break;
                } else if ((indexModerator < indexPos & indexModerator < indexNeg & indexNeg < indexPos)) {
                    tweet.deleteFromListCategories("12");
                    break;
                }
            }

        }
    }

    private void isStatusGarbled() {
//        if (status.contains("Social innovation")) {
//            System.out.println("brass monkey");
//        }
        if (!tweet.getListCategories().isEmpty()) {
            return;
        }
        if (statusStripped.length() < 5) {
            tweet.addToListCategories("92", -1);
        }
        if (statusStripped.split(" ").length < 4) {
            tweet.addToListCategories("92", -1);
        }
    }

    private void whenAllElseFailed() {
        //what to do when a tweet contains both positive and negative markers?
        //classify it as negative, except if it ends by a positive final note
        if (tweet.getListCategories().contains("11") & tweet.getListCategories().contains("12")) {
            if (tweet.getFinalNote() == null || tweet.getFinalNote() == -1) {
                tweet.deleteFromListCategories("11");
            } else if (tweet.getFinalNote() == 1) {
                tweet.deleteFromListCategories("12");
            }
        }

        //indicates when a tweet is positive without being promotted
//        if (tweet.getListCategories().contains("11") & !tweet.getListCategories().contains("061")) {
////                System.out.println("positive tweets without promotion: (user: "+tweet.getUser()+") "+tweet.getText());
//            tweet.addToListCategories("111", -1);
//        }
    }
}
