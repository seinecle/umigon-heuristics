/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.Categories;
import net.clementlevallois.umigon.model.Categories.Category;
import net.clementlevallois.umigon.model.CategoryAndIndex;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristics {

    private LexiconsAndConditionalExpressions heuristic;

    private String lang;

    public HashtagLevelHeuristics(String lang) {
        this.lang = lang;
    }

    public List<CategoryAndIndex> checkAgainstListOfHashtags(HeuristicsLoaderOnDemand heuristics, String hashtag, String text) {
        List<CategoryAndIndex> cats = new ArrayList();

        /*
        
        hashtags in this list are coded with a variety of outcomes (011, 012...)
         so they are not specific to 'sentiment' classification.
        They can be used for emotions, detection of promoted speech...
        
         */
        for (String term : heuristics.getMapH13().keySet()) {
            if (hashtag.contains(term)) {
                heuristic = heuristics.getMapH13().get(term);
                TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics(heuristics);
                List<CategoryAndIndex> catsTemp = termLevelHeuristics.checkFeatures(heuristic, text, text, hashtag, -1, true);
                cats.addAll(catsTemp);
            }
        }
        return cats;
    }

    public List<CategoryAndIndex> isHashTagStartingWithAffectiveTerm(HeuristicsLoaderOnDemand heuristics, String hashtag) {

        List<CategoryAndIndex> cats = new ArrayList();

        boolean startsWithNegativeTerm = false;
        for (String term : heuristics.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getSetNegations()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                startsWithNegativeTerm = true;
                hashtag = hashtag.replace(term, "");
            }
        }
        for (String term : heuristics.getMapH3().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term)) {
                hashtag = hashtag.replace(term, "");
            }
        }

        for (String term : heuristics.getMapH1().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH1().get(term) != null) {
                if (heuristics.getMapH1().get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        cats.add(new CategoryAndIndex(Category._11, -1));
                    } else {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    }
                }
            }
        }
        for (String term : heuristics.getMapH2().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH2().get(term) != null) {
                if (heuristics.getMapH2().get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    } else {
                        cats.add(new CategoryAndIndex(Category._11, -1));
                    }
                }
            }
        }

        for (String term : heuristics.getMapH17().keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH17().get(term) != null) {
                if (heuristics.getMapH17().get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        cats.add(new CategoryAndIndex(Category._17, -1));
                    } else {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    }
                }
            }
        }
        return cats;
    }

    public List<CategoryAndIndex> isHashTagContainingAffectiveTerm(HeuristicsLoaderOnDemand heuristics, String hashtag) {

        List<CategoryAndIndex> cats = new ArrayList();

        for (String term : heuristics.getMapH1().keySet()) {
            if (term.length() < 4 || !heuristics.getMapH1().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._11, -1));
                    } else {
                        if (hashtag.equals(term)) {
                            cats.add(new CategoryAndIndex(Category._11, -1));
                        }
                    }
                } else {
                    cats.add(new CategoryAndIndex(Category._11, -1));
                }
            }
        }

        for (String term : heuristics.getMapH2().keySet()) {
            if (term.length() < 4 || !heuristics.getMapH2().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._11, -1));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    } else {
                        if (hashtag.equals(term)) {
                            cats.add(new CategoryAndIndex(Category._12, -1));
                        }
                    }
                } else {
                    cats.add(new CategoryAndIndex(Category._12, -1));
                }
            }
        }

        for (String term : heuristics.getMapH17().keySet()) {
            if (term.length() < 4 || !heuristics.getMapH17().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._12, -1));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        cats.add(new CategoryAndIndex(Category._17, -1));
                    } else {
                        if (hashtag.equals(term)) {
                            cats.add(new CategoryAndIndex(Category._17, -1));
                        }
                    }
                } else {
                    cats.add(new CategoryAndIndex(Category._17, -1));
                }
            }
        }
        return cats;
    }
}
