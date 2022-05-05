/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.model.Document;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.clementlevallois.umigon.model.Categories.Category;
import net.clementlevallois.utils.StatusCleaner;

/**
 *
 * @author C. Levallois
 */
public class SentenceLevelHeuristicsPost {

    private String text;
    private String textStrippedLowerCase;
    private Document document;
    private final Set<String> ironicallyPositive;
    private final Set<String> setNegations;
    private final Set<String> setModerators;

    public SentenceLevelHeuristicsPost(Set<String> ironicallyPositive, Set<String> setNegations, Set<String> setModerators) {
        this.ironicallyPositive = ironicallyPositive;
        this.setNegations = setNegations;
        this.setModerators = setModerators;
    }

    public void initialize(Document document, String text, String textStrippedLowerCase) {
        this.text = text;
        this.textStrippedLowerCase = textStrippedLowerCase;
        this.document = document;
    }

    public void isIronicallyPositive() {
        if (document.getListCategories().contains(Category._11) & document.getListCategories().contains(Category._12)) {
            for (String irony : ironicallyPositive) {
                if (text.contains(irony)) {
                    document.deleteFromListCategories(Category._12);
                }
            }
        }
    }

    public void containsNegationInCaps() {
        StatusCleaner cleaner = new StatusCleaner();
        text = cleaner.removeStartAndFinalApostrophs(text);
        text = cleaner.removePunctuationSigns(text).trim();

        for (String term : setNegations) {
            if (text.contains(term.toUpperCase())) {
                int indexStrongNegation = text.indexOf(term.toUpperCase());
                document.addToListCategories(Category._12, indexStrongNegation, term);
            }
        }
    }

    public void containsModerator() {
        if (document.getListCategories().isEmpty()) {
            return;
        }
        Set<Integer> indexesPos = document.getAllIndexesForCategory(Category._11);
        Set<Integer> indexesNeg = document.getAllIndexesForCategory(Category._12);

        if (indexesPos.isEmpty() || indexesNeg.isEmpty()) {
            return;
        }
        int indexModerator;
        int indexPosFirst = Integer.MAX_VALUE;
        int indexNegFirst = Integer.MAX_VALUE;
        int indexPosLast = -1;
        int indexNegLast = -1;

        Set<String> termsInText = new HashSet();
        termsInText.addAll(Arrays.asList(textStrippedLowerCase.split(" ")));
        Iterator<Integer> iterator;

        iterator = indexesPos.iterator();
        while (iterator.hasNext()) {
            Integer currIndex = iterator.next();
            if (currIndex < indexPosFirst) {
                indexPosFirst = currIndex;
            }
            if (currIndex > indexPosLast) {
                indexPosLast = currIndex;
            }
        }
        iterator = indexesNeg.iterator();
        while (iterator.hasNext()) {
            Integer currIndex = iterator.next();
            if (currIndex < indexNegFirst) {
                indexNegFirst = currIndex;
            }
            if (currIndex > indexNegLast) {
                indexNegLast = currIndex;
            }
        }

        for (String moderator : setModerators) {
            if (termsInText.contains(moderator)) {
                indexModerator = textStrippedLowerCase.indexOf(moderator);
                if ((indexPosFirst < indexModerator & indexNegFirst > indexModerator)) {
                    document.deleteFromListCategories(Category._11);
                    break;
                } else if ((indexPosFirst > indexModerator & indexNegFirst < indexModerator)) {
                    document.deleteFromListCategories(Category._12);
                    break;
                }
                if ((indexModerator < indexPosFirst & indexModerator < indexNegFirst & indexPosFirst < indexNegFirst)) {
                    document.deleteFromListCategories(Category._11);
                    break;
                }
                if ((indexPosFirst < indexModerator & indexesNeg.isEmpty())) {
                    document.deleteFromListCategories(Category._11);
                    break;
                }
                if (indexNegFirst < indexModerator & indexNegLast < indexModerator) {
                    document.deleteFromListCategories(Category._12);
                    break;
                }
                if (indexPosFirst < indexModerator & indexPosLast < indexModerator) {
                    document.deleteFromListCategories(Category._11);
                    break;
                } else if ((indexModerator < indexPosFirst & indexModerator < indexNegFirst & indexNegFirst < indexPosFirst)) {
                    document.deleteFromListCategories(Category._12);
                    break;
                }

            }
        }
    }

    public void containsANegationAndAPositiveAndNegativeSentiment() {
        Set<Integer> indexesPos = document.getAllIndexesForCategory(Category._11);
        Set<Integer> indexesNeg = document.getAllIndexesForCategory(Category._12);

        if (indexesPos.isEmpty() || indexesNeg.isEmpty()) {
            return;
        }
        int indexNegation;
        int indexPos = 0;
        int indexNeg = 0;

        Set<String> termsInText = new HashSet();
        termsInText.addAll(Arrays.asList(textStrippedLowerCase.split(" ")));
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

        for (String negation : setNegations) {
            if (termsInText.contains(negation + " ")) {
                indexNegation = text.indexOf(negation);
                if ((indexPos < indexNegation & indexNeg > indexNegation)) {
                    document.deleteFromListCategories(Category._11);
                    break;
                } else if ((indexPos > indexNegation & indexNeg < indexNegation)) {
                    document.deleteFromListCategories(Category._12);
                    break;
                }
                if ((indexNegation < indexPos & indexNegation < indexNeg & indexPos < indexNeg)) {
                    document.deleteFromListCategories(Category._11);
                    break;
                } else if ((indexNegation < indexPos & indexNegation < indexNeg & indexNeg < indexPos)) {
                    document.deleteFromListCategories(Category._12);
                    break;
                }
            }
        }
    }

    private void isStatusGarbled() {
        if (!document.getListCategories().isEmpty()) {
            return;
        }
        if (textStrippedLowerCase.length() < 5) {
            document.addToListCategories(Category._92, -1, textStrippedLowerCase);
        }
        if (textStrippedLowerCase.split(" ").length < 4) {
            document.addToListCategories(Category._92, -1, textStrippedLowerCase);
        }
    }

    public void whenAllElseFailed() {
        //what to do when a tweet contains both positive and negative markers?
        //classify it as negative, except if it ends by a positive final note
        if (document.getListCategories().contains(Category._11) & document.getListCategories().contains(Category._12)) {
            if (document.getFinalNote() == null) {
                document.deleteFromListCategories(Category._11);
            } else if (document.getFinalNote() == Category._11) {
                document.deleteFromListCategories(Category._12);
            }
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

}
