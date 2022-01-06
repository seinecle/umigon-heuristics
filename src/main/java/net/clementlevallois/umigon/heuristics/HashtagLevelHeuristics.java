/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.Document;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
//import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author C. Levallois
 */

public class HashtagLevelHeuristics {

    private Document document;
    private String text;
    private LexiconsAndConditionalExpressions heuristic;
    private Set<String> hashtags;
    private Iterator<String> hashtagsIterator;
    private String hashtag;
    private String result;
    private String lang;
    private HeuristicsLoaderOnDemand heuristics;

    public HashtagLevelHeuristics(HeuristicsLoaderOnDemand heuristics) {
        this.heuristics = heuristics;
    }

    public Document applyRules(Document document, String lang) {
        this.document = document;
        this.lang = lang;
        this.text = document.getText();
        this.hashtags = new HashSet();
        hashtags.addAll(document.getHashtags());
        if (hashtags.isEmpty() & text.indexOf("#")!=-1) {
            String[] terms = text.split(" ");
            for (String string : terms) {
                if (string.startsWith("#")) {
                    hashtags.add(string);
                }
            }
        }
        document.getHashtags().addAll(hashtags);

        hashtagsIterator = hashtags.iterator();
        while (hashtagsIterator.hasNext()) {
            hashtag = hashtagsIterator.next().toLowerCase();
            if (isContainedInHashTagHeuristics()) {
                return document;
            }
            isHashTagContainingAnOpinion();
            isHashTagStartingWithAnOpinion();
        }
        return document;
    }

    private boolean isContainedInHashTagHeuristics() {
        for (String term : heuristics.getMapH13(lang).keySet()) {
            if (hashtag.contains(term)) {
                heuristic = heuristics.getMapH13(lang).get(term);
                TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics(heuristics);
                termLevelHeuristics.checkFeatures(heuristic, document.getText(), document.getText(),hashtag,-1,true,lang);
                if (result != null) {
                    document.addToListCategories(result, -2);
                    return true;
                }

            }
        }
        return false;
    }

    private void isHashTagStartingWithAnOpinion() {
        boolean startsWithNegativeTerm = false;
        for (String term : heuristics.getMapH3(lang).keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getSetNegations(lang)) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                startsWithNegativeTerm = true;
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getMapH3(lang).keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }

        for (String term : heuristics.getMapH1(lang).keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)  && heuristics.getMapH1(lang).get(term) !=null) {
                if (heuristics.getMapH1(lang).get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        document.addToListCategories("11", -2);
                        break;
                    } else {
                        document.addToListCategories("12", -2);
                        break;
                    }
                }
            }
        }
        for (String term : heuristics.getMapH2(lang).keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH2(lang).get(term) !=null) {
                if (heuristics.getMapH2(lang).get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        document.addToListCategories("12", -2);
                        break;
                    } else {
                        document.addToListCategories("11", -2);
                        break;
                    }
                }
            }
        }
    }

    private void isHashTagContainingAnOpinion() {
        for (String term : heuristics.getMapH1(lang).keySet()) {
            if (term.length() < 4 || !heuristics.getMapH1(lang).get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations(lang).contains(hashtag)) {
                        document.addToListCategories("12", -2);
                        break;

                    }
                    if (heuristics.getMapH3(lang).keySet().contains(hashtag)) {
                        document.addToListCategories("11", -2);
                        document.addToListCategories("22", -2);
                        break;
                    } else {
                        if (hashtag.equals(term)) {
                            document.addToListCategories("11", -2);
                            break;
                        }
                    }
                } else {
                    document.addToListCategories("11", -2);
                    break;

                }
            }
        }
        for (String term : heuristics.getMapH2(lang).keySet()) {
            if (term.length() < 4|| !heuristics.getMapH2(lang).get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations(lang).contains(hashtag)) {
                        document.addToListCategories("11", -2);
                        break;
                    }
                    if (heuristics.getMapH3(lang).keySet().contains(hashtag)) {
                        document.addToListCategories("12", -2);
                        document.addToListCategories("22", -2);
                        break;
                    } else {
                        if (hashtag.equals(term)) {
                            document.addToListCategories("12", -2);
                            break;
                        }
                    }
                } else {
                    document.addToListCategories("12", -2);
                    break;

                }
            }
        }
    }
}
