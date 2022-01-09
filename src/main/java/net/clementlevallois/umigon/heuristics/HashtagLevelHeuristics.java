/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.umigon.heuristics;

import net.clementlevallois.umigon.heuristics.model.LexiconsAndConditionalExpressions;

/**
 *
 * @author C. Levallois
 */
public class HashtagLevelHeuristics {

    private LexiconsAndConditionalExpressions heuristic;

    public HashtagLevelHeuristics() {
    }

    public String checkAgainstListOfHashtags(HeuristicsLoaderOnDemand heuristics, String lang, String hashtag, String text) {
        String result;
        for (String term : heuristics.getMapH13(lang).keySet()) {
            if (hashtag.contains(term)) {
                heuristic = heuristics.getMapH13(lang).get(term);
                TermLevelHeuristics termLevelHeuristics = new TermLevelHeuristics(heuristics);
                result = termLevelHeuristics.checkFeatures(heuristic, text, text, hashtag, -1, true, lang);
                return result;
            }
        }
        return null;
    }

    public String isHashTagStartingWithAnOpinion(HeuristicsLoaderOnDemand heuristics, String lang, String hashtag) {
        String result = null;
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
            if (hashtag.startsWith(term) && heuristics.getMapH1(lang).get(term) != null) {
                if (heuristics.getMapH1(lang).get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        return "11";
                    } else {
                        return "12";
                    }
                }
            }
        }
        for (String term : heuristics.getMapH2(lang).keySet()) {
            if (term.length() < 4) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.startsWith(term) && heuristics.getMapH2(lang).get(term) != null) {
                if (heuristics.getMapH2(lang).get(term).isHashtagRelevant()) {
                    if (!startsWithNegativeTerm) {
                        return "12";
                    } else {
                        return "11";
                    }
                }
            }
        }
        return null;
    }

    public String isHashTagContainingAnOpinion(HeuristicsLoaderOnDemand heuristics, String lang, String hashtag) {
        for (String term : heuristics.getMapH1(lang).keySet()) {
            if (term.length() < 4 || !heuristics.getMapH1(lang).get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations(lang).contains(hashtag)) {
                        return "12";
                    }
                    if (heuristics.getMapH3(lang).keySet().contains(hashtag)) {
                        return "11";
                    } else {
                        if (hashtag.equals(term)) {
                            return "11";
                        }
                    }
                } else {
                    return "11";
                }
            }
        }
        for (String term : heuristics.getMapH2(lang).keySet()) {
            if (term.length() < 4 || !heuristics.getMapH2(lang).get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (heuristics.getSetNegations(lang).contains(hashtag)) {
                        return "11";
                    }
                    if (heuristics.getMapH3(lang).keySet().contains(hashtag)) {
                        return "12";
                    } else {
                        if (hashtag.equals(term)) {
                            return "12";
                        }
                    }
                } else {
                    return "12";
                }
            }
        }
        return null;
    }
}
