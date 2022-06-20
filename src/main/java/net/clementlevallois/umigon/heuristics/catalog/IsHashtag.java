/*
 * author: Clément Levallois
 */
package net.clementlevallois.umigon.heuristics.catalog;

import net.clementlevallois.umigon.heuristics.tools.LoaderOfLexiconsAndConditionalExpressions;
import net.clementlevallois.umigon.model.ResultOneHeuristics;
import net.clementlevallois.umigon.model.TypeOfToken.TypeOfTokenEnum;
import static net.clementlevallois.umigon.model.ConditionalExpression.ConditionEnum.isHashtag;

/**
 *
 * @author LEVALLOIS
 */
public class IsHashtag {

    public static ResultOneHeuristics check(String text, String termOrig, String termHeuristic, int indexTerm, LoaderOfLexiconsAndConditionalExpressions heuristics) {
        ResultOneHeuristics resultOneHeuristics = new ResultOneHeuristics(isHashtag, termOrig, indexTerm, TypeOfTokenEnum.HASHTAG);
        boolean found = termOrig.toLowerCase().equals(termHeuristic.toLowerCase());
        if (found) {
            resultOneHeuristics.setKeywordMatched(termHeuristic);
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.TRUE);
        } else {
            resultOneHeuristics.setTokenInvestigatedGetsMatched(Boolean.FALSE);
        }
        return resultOneHeuristics;

    }
}
