/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import java.util.ArrayList;
import java.util.List;
import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.Category;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashTagStartingWithAffectiveTerm {

    public static List<ResultOneHeuristics> check(LoaderOfLexiconsAndConditionalExpressions heuristics, String hashtag) {

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
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
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
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._17, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
                    } else {
                        resultsHeuristics.add(new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG));
                    }
                }
            }
        }
        return resultsHeuristics;
    }

}
