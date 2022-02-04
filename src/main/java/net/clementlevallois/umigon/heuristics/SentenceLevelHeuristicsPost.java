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

    private String text;
    private String textStripped;
    private Document document;

    public void initialize(Document document, String text, String textStripped) {
        this.text = text;
        this.document = document;
        this.textStripped = textStripped;
    }

    public Document isIronicallyPositive(Set<String> ironicallyPositive) {
        if (document.getListCategories().contains("11") & document.getListCategories().contains("12")) {
            for (String irony : ironicallyPositive) {
                if (text.contains(irony)) {
                    document.deleteFromListCategories("12");
                }
            }
        }
        return document;

    }

    public Document containsNegation(Set<String> setNegations) {
        StatusCleaner cleaner = new StatusCleaner();
        text = cleaner.removeStartAndFinalApostrophs(text);
        text = cleaner.removePunctuationSigns(text).toLowerCase().trim();

        Set<String> termsInStatus = new HashSet();
        termsInStatus.addAll(Arrays.asList(text.split(" ")));
        if (!document.getListCategories().isEmpty()) {
            return document;
        }
        for (String term : setNegations) {
            if (termsInStatus.contains(term)) {
                document.addToListCategories("12", -1);
            }
        }
        return document;
    }

    public Document containsModerator(Set<String> moderators) {
        if (document.getListCategories().isEmpty()) {
            return document;
        }
        String textStrippedLowerCase = textStripped.toLowerCase();
        for (String moderator : moderators) {
            if (textStrippedLowerCase.contains(moderator)) {
                int indexMod = textStrippedLowerCase.indexOf(moderator);
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
        return document;
    }

    public Document containsANegationAndAPositiveAndNegativeSentiment(Set<String> negations) {
        Set<Integer> indexesPos = document.getAllIndexesForCategory("11");
        Set<Integer> indexesNeg = document.getAllIndexesForCategory("12");

        if (indexesPos.isEmpty() || indexesNeg.isEmpty()) {
            return document;
        }
        int indexNegation;
        int indexPos = 0;
        int indexNeg = 0;

        Set<String> termsInText = new HashSet();
        termsInText.addAll(Arrays.asList(textStripped.split(" ")));
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

        for (String negation : negations) {
            if (termsInText.contains(negation)) {
                indexNegation = text.indexOf(negation);
                if ((indexPos < indexNegation & indexNeg > indexNegation)) {
                    document.deleteFromListCategories("11");
                    break;
                } else if ((indexPos > indexNegation & indexNeg < indexNegation)) {
                    document.deleteFromListCategories("12");
                    break;
                }
                if ((indexNegation < indexPos & indexNegation < indexNeg & indexPos < indexNeg)) {
                    document.deleteFromListCategories("11");
                    break;
                } else if ((indexNegation < indexPos & indexNegation < indexNeg & indexNeg < indexPos)) {
                    document.deleteFromListCategories("12");
                    break;
                }
            }
        }
        return document;
    }

    private Document isStatusGarbled() {
        if (!document.getListCategories().isEmpty()) {
            return document;
        }
        if (textStripped.length() < 5) {
            document.addToListCategories("92", -1);
        }
        if (textStripped.split(" ").length < 4) {
            document.addToListCategories("92", -1);
        }
        return document;
    }

    public Document whenAllElseFailed() {
        //what to do when a tweet contains both positive and negative markers?
        //classify it as negative, except if it ends by a positive final note
        if (document.getListCategories().contains("11") & document.getListCategories().contains("12")) {
            if (document.getFinalNote() == null || document.getFinalNote() == -1) {
                document.deleteFromListCategories("11");
            } else if (document.getFinalNote() == 1) {
                document.deleteFromListCategories("12");
            }
        }
        return document;

        //indicates when a tweet is positive without being promotted
//        if (tweet.getListCategories().contains("11") & !tweet.getListCategories().contains("061")) {
////                System.out.println("positive tweets without promotion: (user: "+tweet.getUser()+") "+tweet.getText());
//            tweet.addToListCategories("111", -1);
//        }
    }
}
