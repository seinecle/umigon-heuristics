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
    private Document document;
    private String lang;
    private final HeuristicsLoaderOnDemand heuristics;

    public SentenceLevelHeuristicsPost(HeuristicsLoaderOnDemand heuristics) {
        this.heuristics = heuristics;
    }

    public Document applyRules(Document tweet, String status, String statusStripped, String lang) {
        this.status = status;
        this.document = tweet;
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


    public void isIronicallyPositive() {
        if (document.getListCategories().contains("11") & document.getListCategories().contains("12")) {
            for (String term : heuristics.getSetIronicallyPositive(lang)) {
                if (status.contains(term)) {
                    document.deleteFromListCategories("12");
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
        if (!document.getListCategories().isEmpty()) {
            return;
        }
        for (String term : heuristics.getSetNegations(lang)) {
            if (termsInStatus.contains(term)) {
                document.addToListCategories("12", -1);
            }
        }
    }

    public void containsModerator() {
        if (document.getListCategories().isEmpty()) {
            return;
        }
        String statusStrippedLowerCase = statusStripped.toLowerCase();
        for (String term : heuristics.getSetModerators(lang)) {
            if (statusStrippedLowerCase.contains(term)) {
                int indexMod = statusStrippedLowerCase.indexOf(term);
                Queue<CategoryAndIndex> mapCategoriesToIndex = document.getMapCategoriesToIndex();
                Iterator<CategoryAndIndex> iterator = mapCategoriesToIndex.iterator();
                while (iterator.hasNext()) {
                    CategoryAndIndex next = iterator.next();
                    if (next.getIndex() > indexMod) {
                        iterator.remove();
                        document.getListCategories().remove(next.getCategory());
                    }
                }
            }
        }
    }

    public void containsANegationAndAPositiveAndNegativeSentiment() {
        Set<Integer> indexesPos = document.getAllIndexesForCategory("11");
        Set<Integer> indexesNeg = document.getAllIndexesForCategory("12");

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
                    document.deleteFromListCategories("11");
                    break;
                } else if ((indexPos > indexModerator & indexNeg < indexModerator)) {
                    document.deleteFromListCategories("12");
                    break;
                }
                if ((indexModerator < indexPos & indexModerator < indexNeg & indexPos < indexNeg)) {
                    document.deleteFromListCategories("11");
                    break;
                } else if ((indexModerator < indexPos & indexModerator < indexNeg & indexNeg < indexPos)) {
                    document.deleteFromListCategories("12");
                    break;
                }
            }

        }
    }

    private void isStatusGarbled() {
//        if (status.contains("Social innovation")) {
//            System.out.println("brass monkey");
//        }
        if (!document.getListCategories().isEmpty()) {
            return;
        }
        if (statusStripped.length() < 5) {
            document.addToListCategories("92", -1);
        }
        if (statusStripped.split(" ").length < 4) {
            document.addToListCategories("92", -1);
        }
    }

    private void whenAllElseFailed() {
        //what to do when a tweet contains both positive and negative markers?
        //classify it as negative, except if it ends by a positive final note
        if (document.getListCategories().contains("11") & document.getListCategories().contains("12")) {
            if (document.getFinalNote() == null || document.getFinalNote() == -1) {
                document.deleteFromListCategories("11");
            } else if (document.getFinalNote() == 1) {
                document.deleteFromListCategories("12");
            }
        }

        //indicates when a tweet is positive without being promotted
//        if (tweet.getListCategories().contains("11") & !tweet.getListCategories().contains("061")) {
////                System.out.println("positive tweets without promotion: (user: "+tweet.getUser()+") "+tweet.getText());
//            tweet.addToListCategories("111", -1);
//        }
    }
}
