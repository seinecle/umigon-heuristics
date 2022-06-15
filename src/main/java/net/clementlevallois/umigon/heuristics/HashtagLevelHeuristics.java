/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.model.Categories;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.heuristics.LexiconsAndConditionalExpressions;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristics {

    private LexiconsAndConditionalExpressions heuristic;


    public HashtagLevelHeuristics() {
    }

    public List<ResultOneHeuristics> checkAgainstListOfHashtags(HeuristicsLoaderOnDemand heuristics, String hashtag, String text) {
        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        /*
        
        hashtags in this list are coded with a variety of outcomes (011, 012...)
         so they are not specific to 'sentiment' classification.
        They can be used for emotions, detection of promoted speech...
        
         */
        for (String term : heuristics.getMapH13().keySet()) {
            if (hashtag.contains(term)) {
                heuristic = heuristics.getMapH13().get(term);
                TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics(heuristics);
                List<ResultOneHeuristics> resultsTempHeuristics = termLevelHeuristics.checkFeatures(heuristic, text, text, hashtag, -1, true);
                resultsHeuristics.addAll(resultsTempHeuristics);
            }
        }
        return resultsHeuristics;
    }

    public List<ResultOneHeuristics> isHashTagStartingWithAffectiveTerm(HeuristicsLoaderOnDemand heuristics, String hashtag) {

        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

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
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("17"), -1, hashtag));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                    }
                }
            }
        }
        return resultsHeuristics;
    }

    public List<ResultOneHeuristics> isHashTagContainingAffectiveTerm(HeuristicsLoaderOnDemand heuristics, String hashtag) {

        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        for (String term : heuristics.getMapH1().keySet()) {
            if (term.length() < 4 || !heuristics.getMapH1().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations().contains(hashtag)) {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
                    } else {
                        if (hashtag.equals(term)) {
                            resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
                        }
                    }
                } else {
                    resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("11"), -1, hashtag));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                    } else {
                        if (hashtag.equals(term)) {
                            resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                        }
                    }
                } else {
                    resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("12"), -1, hashtag));
                    }
                    if (heuristics.getMapH3().keySet().contains(hashtag)) {
                        resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("17"), -1, hashtag));
                    } else {
                        if (hashtag.equals(term)) {
                            resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("17"), -1, hashtag));
                        }
                    }
                } else {
                    resultsHeuristics.add(new ResultOneHeuristics(Categories.getCategory("17"), -1, hashtag));
                }
            }
        }
        return resultsHeuristics;
    }
}
