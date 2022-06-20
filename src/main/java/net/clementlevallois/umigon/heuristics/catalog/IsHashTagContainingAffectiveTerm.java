/*
 * author: Cl�ment Levallois
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
public class IsHashTagContainingAffectiveTerm {

    public static List<ResultOneHeuristics> check(LoaderOfLexiconsAndConditionalExpressions lexiconsAndTheirConditionalExpressions, String hashtag) {

        List<ResultOneHeuristics> resultsHeuristics = new ArrayList();

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH1().keySet()) {
            if (term.length() < 4 || !lexiconsAndTheirConditionalExpressions.getMapH1().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (lexiconsAndTheirConditionalExpressions.getSetNegations().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    }
                    if (lexiconsAndTheirConditionalExpressions.getMapH3().keySet().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    } else {
                        if (hashtag.equals(term)) {
                            ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            resultsHeuristics.add(resultOneHeuristics);
                        }
                    }
                } else {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    resultsHeuristics.add(resultOneHeuristics);
                }
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH2().keySet()) {
            if (term.length() < 4 || !lexiconsAndTheirConditionalExpressions.getMapH2().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (lexiconsAndTheirConditionalExpressions.getSetNegations().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._11, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    }
                    if (lexiconsAndTheirConditionalExpressions.getMapH3().keySet().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    } else {
                        if (hashtag.equals(term)) {
                            ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            resultsHeuristics.add(resultOneHeuristics);
                        }
                    }
                } else {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    resultsHeuristics.add(resultOneHeuristics);
                }
            }
        }

        for (String term : lexiconsAndTheirConditionalExpressions.getMapH17().keySet()) {
            if (term.length() < 4 || !lexiconsAndTheirConditionalExpressions.getMapH17().get(term).isHashtagRelevant()) {
                continue;
            }
            term = term.replace(" ", "");
            if (hashtag.contains(term)) {
                hashtag = hashtag.replace(term, "");
                if (hashtag.length() > 1) {
                    if (lexiconsAndTheirConditionalExpressions.getSetNegations().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._12, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    }
                    if (lexiconsAndTheirConditionalExpressions.getMapH3().keySet().contains(hashtag)) {
                        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._17, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                        resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                        resultsHeuristics.add(resultOneHeuristics);
                    } else {
                        if (hashtag.equals(term)) {
                            ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._17, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                            resultsHeuristics.add(resultOneHeuristics);
                        }
                    }
                } else {
                    ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(Category.CategoryEnum._17, -1, hashtag, TypeOfToken.TypeOfTokenEnum.HASHTAG);
                    resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
                    resultsHeuristics.add(resultOneHeuristics);
                }
            }
        }
        return resultsHeuristics;
    }
}